package com.eventonline.controller.usuario;

import com.eventonline.model.Usuario;
import com.eventonline.service.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "editar-perfil", value = "/app/editarPerfil")
public class EditarPerfilServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("UsuarioLog") == null) {
            resp.sendRedirect(req.getContextPath() + "/app/login");
            return;
        }

        Usuario usuarioLog = (Usuario) session.getAttribute("UsuarioLog");

        String nombre = req.getParameter("name");
        String telefono = req.getParameter("telefono");
        String ciudad = req.getParameter("ciudad");

        usuarioLog.setNombre(nombre);
        usuarioLog.setTelefono(telefono);
        usuarioLog.setCiudad(ciudad);

        try {

            usuarioService.actualizarPerfil(usuarioLog);

            // Actualizar el objeto de la sesión
            session.setAttribute("UsuarioLog", usuarioLog);

            resp.sendRedirect(req.getContextPath() + "/app/perfil");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}