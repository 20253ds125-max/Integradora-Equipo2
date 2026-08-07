package com.eventonline.controller.carrito.pago;

import com.eventonline.model.Reservacion;
import com.eventonline.service.ReservacionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/pago")
public class VerPagoServlet extends HttpServlet {

    private final ReservacionService reservacionService = new ReservacionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 📍 1. Recuperamos el ID de la reserva generado en el POST
        Integer idReserva = (Integer) session.getAttribute("idReservaPendiente");

        if (idReserva == null) {
            // Si intenta entrar directo a /pago sin haber pasado por el carrito
            response.sendRedirect(request.getContextPath() + "/mi-carrito");
            return;
        }

        try {
            // 📍 2. Consultamos la reserva desde la BD mediante Service y DAO
            Reservacion reservacion = reservacionService.obtenerReservaPorId(idReserva);

            // Validamos que exista y que siga PENDIENTE
            if (reservacion == null || !"PENDIENTE".equals(reservacion.getEstado())) {
                session.removeAttribute("idReservaPendiente");
                request.setAttribute("error", "La reserva ya no está disponible o ha expirado.");
                request.getRequestDispatcher("/WEB-INF/mi-carrito-de-compra.jsp").forward(request, response);
                return;
            }

            // 📍 3. Adjuntamos el objeto a la petición para que la JSP renderice los importes
            request.setAttribute("reservacion", reservacion);

            // 📍 4. Mostramos la pantalla de pago de forma segura
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/mi-carrito");
        }
    }
}
