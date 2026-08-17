package com.eventonline.controller.carrito.pago;

import com.eventonline.model.Usuario;
import com.eventonline.service.CarritoService;
import com.eventonline.service.PagoService;
import com.eventonline.service.ReservacionService;

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

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");


        String titular = request.getParameter("nombreTitular");
        String tarjeta = request.getParameter("numeroTarjeta");
        String vencimiento = request.getParameter("vencimiento");
        String cvv = request.getParameter("cvv");

        boolean tarjetaValida = pagoService.validarTarjeta(titular, tarjeta, vencimiento, cvv);

        if (!tarjetaValida) {
            request.setAttribute("error", "datos_invalidos");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            return;
        }


        Integer idReserva = (Integer) session.getAttribute("idReservaPendiente");
        if (idReserva == null && request.getParameter("idReserva") != null && !request.getParameter("idReserva").isEmpty()) {
            idReserva = Integer.parseInt(request.getParameter("idReserva"));
        }

        if (idReserva == null) {
            request.setAttribute("error", "sin_reserva");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            return;
        }

        // 3. Verificar si el usuario ya aceptó el modal de confirmación
        String confirmado = request.getParameter("confirmado");
        if (!"true".equals(confirmado)) {
            // Todos los datos son válidos; solicitamos la confirmación al cliente
            request.setAttribute("status", "pedir_confirmacion");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            return;
        }

        // 4. Procesar confirmación en BD y limpiar carrito tras la confirmación previa
        try {
            boolean exito = reservacionService.confirmarPagoReserva(idReserva);

            if (exito) {
                carritoService.vaciarCarrito(usuario.getIdUsuario());
                session.removeAttribute("idReservaPendiente");


                request.setAttribute("status", "exito");
                request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "reserva_expirada");
                request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "db_error");
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);
        }
    }
}