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

@WebServlet("/agregarCarrito")
public class AgregarServicioCarrito extends HttpServlet {

    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            request.setAttribute("error", "Debes iniciar sesión para agregar al carrito.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        try {

            int idServicio = Integer.parseInt(request.getParameter("idServicio"));
            Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
            int idUsuario= usuario.getIdUsuario();

            boolean agregado = carritoService.agregarServicioCarrito(idUsuario, idServicio);

            if (agregado) {
                request.setAttribute("exito", "Servicio agregado al carrito con éxito.");
            } else {
                request.setAttribute("error", "No se pudo agregar el servicio.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID del servicio no es válido.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error de base de datos al guardar en el carrito.");
        }catch (IllegalArgumentException e){
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/extraServices").forward(request, response);
    }
}