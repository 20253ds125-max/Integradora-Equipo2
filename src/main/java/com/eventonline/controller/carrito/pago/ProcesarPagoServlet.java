package com.eventonline.controller.carrito.pago;

import com.eventonline.model.Usuario;
import com.eventonline.service.CarritoService;
import com.eventonline.service.PagoService;
import com.eventonline.service.ReservacionService;

import com.eventonline.util.CorreoElectronico;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/procesarPago")
public class ProcesarPagoServlet extends HttpServlet {

    private final ReservacionService reservacionService = new ReservacionService();
    private final CarritoService carritoService = new CarritoService();
    private final PagoService pagoService = new PagoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 0. Si se solicita desplegar el ticket tras presionar "Ver ticket" en la alerta de éxito
        String mostrarTicket = request.getParameter("mostrarTicket");
        if ("true".equals(mostrarTicket)) {
            String idReservaForm = request.getParameter("idReserva");
            if (idReservaForm != null && !idReservaForm.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idReservaForm);
                    String titular = request.getParameter("nombreTitular");
                    String tarjeta = request.getParameter("numeroTarjeta");
                    String tarjetaLimpia = (tarjeta != null) ? tarjeta.replaceAll("\\s+", "") : "";
                    String ultimos4 = (tarjetaLimpia.length() >= 4) ? tarjetaLimpia.substring(tarjetaLimpia.length() - 4) : "****";

                    request.setAttribute("reserva", reservacionService.obtenerReservaConDetallePorId(id));
                    request.setAttribute("titular", titular);
                    request.setAttribute("ultimos4", ultimos4);
                    request.setAttribute("fechaConfirmacion",
                            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                    // Muestra directamente la pantalla final del ticket
                    request.getRequestDispatcher("/WEB-INF/ticket-pago.jsp").forward(request, response);
                    return;
                } catch (SQLException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
        Integer idReservas = (Integer) session.getAttribute("idReservaPendiente");

        if (idReservas == null){
            response.sendRedirect(request.getContextPath() + "/mi-carrito-de-compra");
            return;
        }

        // 1. Obtener datos del formulario
        String titular = request.getParameter("nombreTitular");
        String tarjeta = request.getParameter("numeroTarjeta");
        String vencimiento = request.getParameter("vencimiento");
        String cvv = request.getParameter("cvv");

        // 2. Validar con mensaje de error específico
        String mensajeError = pagoService.obtenerErrorTarjeta(titular, tarjeta, vencimiento, cvv);

        if (mensajeError != null) {
            try {
                request.setAttribute("reservacion", reservacionService.obtenerReservaPorId(idReservas));
            } catch (SQLException ignored) {}

            request.setAttribute("errorDetallado", mensajeError);
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            return;
        }

        // 3. Confirmación previa al cobro
        String confirmado = request.getParameter("confirmado");
        if (!"true".equals(confirmado)) {
            try {
                request.setAttribute("reservacion", reservacionService.obtenerReservaPorId(idReservas));
            } catch (SQLException ignored) {}

            request.setAttribute("status", "pedir_confirmacion");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            return;
        }

        // 4. Procesar el pago
        try {
            boolean exito = reservacionService.confirmarPagoReserva(idReservas);

            if (exito) {
                carritoService.vaciarCarrito(usuario.getIdUsuario());

                // Mantenemos los datos de la reserva en el request para presentarlos en pago.jsp
                request.setAttribute("reservacion", reservacionService.obtenerReservaPorId(idReservas));
                request.setAttribute("status", "exito");

                // Una vez guardada la reserva en la BD y asegurados los datos en la vista, se remueve de la sesión
                session.removeAttribute("idReservaPendiente");

                request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "reserva_expirada");
                request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                request.setAttribute("reservacion", reservacionService.obtenerReservaPorId(idReservas));
            } catch (SQLException ignored) {}
            request.setAttribute("errorDetallado", "Error al procesar el pago en la base de datos.");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
        }
    }
}