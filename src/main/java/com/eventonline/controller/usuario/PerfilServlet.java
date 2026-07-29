package com.eventonline.controller.usuario;

import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet (name = "perfil", value = "/app/perfil")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtiene la sesion pero sin crear una nueva
        HttpSession session = req.getSession(false);

        // Si no hay una sesion o no hay un usario autenticado redirije al login
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            resp.sendRedirect(req.getContextPath() + "/app/login");
            return;
        }

        // Recuperar el usuario autenticado
        Usuario usuarioLogueado = (Usuario) session.getAttribute("UsuarioLog");

        // Pasarle el usuario al jsp
        req.setAttribute("usuario", usuarioLogueado);

        // Ahora si redirir al perfil
        req.getRequestDispatcher("/WEB-INF/perfil.jsp")
                .forward(req, resp);

    }
}
