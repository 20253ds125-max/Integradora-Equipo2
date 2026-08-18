package com.eventonline.controller.usuario;

import com.eventonline.model.Usuario;
import com.eventonline.service.FavoritosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet (name = "favorito-servlet", value = "/app/favoritos")
public class FavoritosServlet extends HttpServlet {

    private final FavoritosService favoritoService = new FavoritosService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Validar que existe una sesion
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("UsuarioLog") == null) {

            HttpSession currentSession = req.getSession(true);
            currentSession.setAttribute("error", "Para agregar a favoritos un recinto primero inicia sesion");

            resp.sendRedirect(req.getContextPath() + "/app/login");
            return;
        }

        // Obtener el usuario autenticado
        Usuario usuarioLog = (Usuario) session.getAttribute("UsuarioLog");

        int idRecinto = Integer.parseInt(req.getParameter("idRecinto"));

        //Delegar el trabajo al FavoritosService

        try {

            favoritoService.cambiarFavorito(
                    usuarioLog.getIdUsuario(),
                    idRecinto
            );

            String origen = req.getHeader("referer");
            if (origen != null) {
                resp.sendRedirect(origen);
            } else {
                resp.sendRedirect(req.getContextPath() + "/app/perfil");
            }
        } catch (SQLException e) {

            throw new ServletException(e);

        }
    }
}
