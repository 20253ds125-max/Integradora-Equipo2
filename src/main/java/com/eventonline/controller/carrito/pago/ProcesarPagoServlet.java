package com.eventonline.controller.carrito.pago;

import com.eventonline.model.Usuario;
import com.eventonline.service.CarritoService;
import com.eventonline.service.ReservacionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;

@WebServlet("/procesarPago")
public class ProcesarPagoServlet extends HttpServlet {

    private final ReservacionService reservacionService = new ReservacionService();
    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
        Integer idReservas = (Integer) session.getAttribute("idReservaPendiente");

        if (idReservas == null){
            response.sendRedirect(request.getContextPath() + "/mi-carrito-de-compra");
            return;
        }
        String error = validarDatosTarjeta(request);
        if (error != null){
            try {
                request.setAttribute("reservacion", reservacionService.obtenerReservaPorId(idReservas));

            }catch (SQLException ignored){

            }
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request,response);
            return;
        }

        try {

            boolean exito = reservacionService.confirmarPagoReserva(idReservas);

            if (exito) {
                carritoService.vaciarCarrito(usuario.getIdUsuario());
                session.removeAttribute("idReservaPendiente");

                String titular = request.getParameter("nombreTitular");
                String tarjeta = request.getParameter("numeroTarjeta").replace(" ", "");
                String ultimos4 = tarjeta.substring(tarjeta.length() - 4);

                request.setAttribute("reserva", reservacionService.obtenerReservaConDetallePorId(idReservas));
                request.setAttribute("titular", titular);
                request.setAttribute("ultimos4", ultimos4);
                request.setAttribute("fechaConfirmacion", java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                request.getRequestDispatcher("/WEB-INF/ticket-pago.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "El tiempo de apartado ha expirado. Por favor intenta de nuevo.");
                request.getRequestDispatcher("/WEB-INF/mi-carrito-de-compra.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar el pago en la base de datos.");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
        }
    }


    private String validarDatosTarjeta(HttpServletRequest request) {
        String titular = request.getParameter("nombreTitular");
        String tarjeta = request.getParameter("numeroTarjeta");
        String vencimiento = request.getParameter("vencimiento");
        String cvv = request.getParameter("cvv");

        String tarjetaLimpia = (tarjeta != null) ? tarjeta.replace(" ", "") : "";
        String vencimientoLimpio = (vencimiento != null) ? vencimiento.trim() : "";
        String cvvLimpio = (cvv != null) ? cvv.trim() : "";

        if (titular == null || titular.trim().isEmpty()) {
            return "Ingresa el nombre del titular de la tarjeta.";
        }
        if (!tarjetaLimpia.matches("^[0-9]{16}$")) {
            return "El número de tarjeta debe tener 16 dígitos.";
        }

        boolean formatoFechaValido = vencimientoLimpio.matches("^(0[1-9]|1[0-2])/[0-9]{2}$");
        if (!formatoFechaValido) {
            return "El formato de vencimiento debe ser MM/YY.";
        }

        String[] partes = vencimientoLimpio.split("/");
        int mesUsuario = Integer.parseInt(partes[0]);
        int anoUsuario = Integer.parseInt("20" + partes[1]);

        YearMonth fechaActual = YearMonth.now();
        YearMonth fechaTarjeta = YearMonth.of(anoUsuario, mesUsuario);

        if (fechaTarjeta.isBefore(fechaActual)) {
            return "La tarjeta ingresada ya está vencida.";
        }

        if (!cvvLimpio.matches("^[0-9]{3,4}$")) {
            return "El CVV debe tener 3 o 4 dígitos.";
        }

        return null;
    }
}
