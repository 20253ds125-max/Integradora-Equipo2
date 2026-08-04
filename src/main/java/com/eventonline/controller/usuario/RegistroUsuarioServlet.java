package com.eventonline.controller.usuario;

import com.eventonline.model.Usuario;
import com.eventonline.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registro", value = "/registro")
public class RegistroUsuarioServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rol = "usuario";
        String telefono = request.getParameter("telefono");
        String ciudad = request.getParameter("ciudad");

        try {

            Usuario usuario = new Usuario(email, nombre, pass, rol, telefono, ciudad);

            usuarioService.registrarUsuario(usuario);

            response.sendRedirect(request.getContextPath()+"/");

        } catch (IllegalArgumentException e) {

            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request,response);

        } catch (SQLException e) {

            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request,response);

        }
    }
}
