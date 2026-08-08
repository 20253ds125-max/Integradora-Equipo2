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
        Integer idReserva = (Integer) session.getAttribute("idReservaPendiente");

        if (idReserva == null) {

            response.sendRedirect(request.getContextPath() + "/mi-carrito");
            return;
        }

        try {
            Reservacion reservacion = reservacionService.obtenerReservaPorId(idReserva);

            if (reservacion == null || !"PENDIENTE".equals(reservacion.getEstado())) {
                session.removeAttribute("idReservaPendiente");
                request.setAttribute("error", "La reserva ya no está disponible o ha expirado.");
                request.getRequestDispatcher("/WEB-INF/mi-carrito-de-compra.jsp").forward(request, response);
                return;
            }

            request.setAttribute("reservacion", reservacion);
            request.getRequestDispatcher("/WEB-INF/pago.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/mi-carrito");
        }
    }
}
