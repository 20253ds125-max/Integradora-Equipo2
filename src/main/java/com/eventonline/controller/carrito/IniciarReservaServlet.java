package com.eventonline.controller.carrito;

import com.eventonline.model.ItemCarrito;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/iniciarReserva")
public class IniciarReservaServlet extends HttpServlet {

    private final CarritoService carritoService = new CarritoService();
    private final ReservacionService reservacionService = new ReservacionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");

        try {
            String fechaEventoStr = request.getParameter("fechaEvento");

            if (fechaEventoStr == null || fechaEventoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Debes seleccionar una fecha para tu evento.");
            }

            try {
                LocalDate fechaEvento = LocalDate.parse(fechaEventoStr);
                LocalDate manana = LocalDate.now().plusDays(1);

                if (fechaEvento.isBefore(manana)) {
                    throw new IllegalArgumentException("La fecha del evento debe ser a partir de mañana.");
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato de fecha ingresado no es válido.");
            }

            List<ItemCarrito> items = carritoService.obtenerItemsPorUsuario(usuario.getIdUsuario());

            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("Tu carrito está vacío.");
            }

            double subtotal = items.stream().mapToDouble(ItemCarrito::getPrecio).sum();
            double deposito = subtotal * 0.30;
            double total = subtotal + deposito;

            int idReserva = reservacionService.procesarBloqueoReserva(
                    usuario.getIdUsuario(),
                    fechaEventoStr,
                    total,
                    items
            );
            session.setAttribute("idReservaPendiente", idReserva);

            response.sendRedirect(request.getContextPath() + "/pago");

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/mi-carrito-de-compra").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al verificar la disponibilidad en la base de datos.");
            request.getRequestDispatcher("/mi-carrito-de-compra").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirige limpiamente de vuelta al carrito si entran por URL directa
        resp.sendRedirect(req.getContextPath() + "/mi-carrito-de-compra");
    }
}