package com.eventonline.controller.usuario;

import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.FavoritosService;
import com.eventonline.service.PerfilService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet (name = "perfil", value = "/app/perfil")
public class PerfilServlet extends HttpServlet {

    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtiene la sesion pero sin crear una nueva
        HttpSession session = req.getSession(false);

        // Si no hay una sesion o no hay un usario autenticado redirije al login
        if (session == null || session.getAttribute("UsuarioLog") == null) {

            req.setAttribute("error", "Primero inicia sesion");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
            return;

        }

        try {


            // Recuperar el usuario autenticado
            Usuario usuarioLog = (Usuario) session.getAttribute("UsuarioLog");

            // Obteniendo favoritos con perfilService
            List<SalonEventos> favoritos =
                    perfilService.obtenerFavoritos(usuarioLog.getIdUsuario());

            // Pasarle el usuario al jsp + obtener favoritos
            req.setAttribute("usuario", usuarioLog);
            req.setAttribute("favoritos", favoritos);

            // Ahora si redirir al perfil
            req.getRequestDispatcher("/WEB-INF/perfil.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {

            throw new ServletException(e);

        }


    }
}
