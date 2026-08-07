package com.eventonline.controller.carrito;

import com.eventonline.model.Usuario;
import com.eventonline.service.CarritoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/vaciarCarrito")
public class VaciarCarritoServlet extends HttpServlet {

    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            request.setAttribute("error", "Debes iniciar sesión.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");

        try {
            boolean vaciado = carritoService.vaciarCarrito(usuario.getIdUsuario());

            if (vaciado) {
                request.setAttribute("exito", "El carrito se ha vaciado correctamente.");
            } else {
                request.setAttribute("error", "El carrito ya estaba vacío.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al intentar vaciar el carrito.");
        }

        request.getRequestDispatcher("/mi-carrito-de-compra").forward(request, response);
    }
}
