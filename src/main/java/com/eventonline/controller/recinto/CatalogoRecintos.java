package com.eventonline.controller.recinto;

import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.RecintoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "catalogoServlet", value = "/catalogo")
public class CatalogoRecintos extends HttpServlet {

    public final RecintoService recintoService = new RecintoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Integer idUsuario = null;

        if (session != null && session.getAttribute("UsuarioLog") != null) {

            Usuario usuarioLog = (Usuario) session.getAttribute("UsuarioLog");

            idUsuario = usuarioLog.getIdUsuario();
        }

        try {

            List<SalonEventos> catalogo = recintoService.obtenerCatalogo(idUsuario);

            request.setAttribute("catalogo", catalogo);

            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);

        } catch (SQLException e){
            request.setAttribute("error","Error al cargar los recintos: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
