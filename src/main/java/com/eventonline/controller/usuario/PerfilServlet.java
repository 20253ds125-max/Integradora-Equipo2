package com.eventonline.controller.usuario;

import com.eventonline.model.ReservaConDetalle;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.FavoritosService;
import com.eventonline.service.PerfilService;
import com.eventonline.service.ReservacionService;
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
    private final ReservacionService reservacionService = new ReservacionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("UsuarioLog") == null) {

            req.setAttribute("error", "Primero inicia sesion");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
            return;

        }

        try {


            Usuario usuarioLog = (Usuario) session.getAttribute("UsuarioLog");

            List<SalonEventos> favoritos =
                    perfilService.obtenerFavoritos(usuarioLog.getIdUsuario());

            List<SalonEventos> publicaciones =
                    perfilService.obtenerPublicaciones(usuarioLog.getIdUsuario());

            List<ReservaConDetalle> reservas =
                    reservacionService.obtenerReservasConDetallePorUsuario(usuarioLog.getIdUsuario());

            req.setAttribute("usuario", usuarioLog);
            req.setAttribute("favoritos", favoritos);
            req.setAttribute("publicaciones", publicaciones);
            req.setAttribute("reservas", reservas);

            req.getRequestDispatcher("/WEB-INF/perfil.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {

            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);

        }


    }
}
