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

@WebServlet("/procesarPago")
public class ProcesarPagoServlet extends HttpServlet {

    private final ReservacionService reservacionService = new ReservacionService();
    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
        Integer idReserva = (Integer) session.getAttribute("idReservaPendiente");

        if (idReserva == null) {
            response.sendRedirect(request.getContextPath() + "/mi-carrito-de-compra");
            return;
        }

        try {
            // 📍 1. Confirmar la reserva en Oracle (PENDIENTE -> CONFIRMADA)
            boolean exito = reservacionService.confirmarPagoReserva(idReserva);

            if (exito) {
                // 📍 2. Vaciar el carrito de compras del usuario en la BD
                carritoService.vaciarCarrito(usuario.getIdUsuario());

                // 📍 3. Limpiar la variable temporal de la sesión
                session.removeAttribute("idReservaPendiente");

                // 📍 4. Guardar mensaje de éxito y dirigir al perfil / confirmación
                session.setAttribute("mensajeExito", "¡Reserva confirmada con éxito!");
                response.sendRedirect(request.getContextPath() + "/app/perfil");
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
}