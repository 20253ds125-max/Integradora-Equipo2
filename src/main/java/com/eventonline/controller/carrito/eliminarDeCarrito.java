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

@WebServlet("/eliminarItemCarrito")
public class eliminarDeCarrito extends HttpServlet {
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
            int idCarrito = Integer.parseInt(request.getParameter("idCarrito"));

            boolean eliminado = carritoService.eliminarItemCarrito(idCarrito, usuario.getIdUsuario());

            if (eliminado) {
                request.setAttribute("exito", "Producto eliminado con éxito.");
            } else {
                request.setAttribute("error", "No se pudo eliminar el producto.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID del ítem es inválido.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos al intentar eliminar.");
        }

        request.getRequestDispatcher("mi-carrito-de-compra").forward(request, response);
    }
}
