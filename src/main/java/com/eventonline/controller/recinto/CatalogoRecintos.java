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

        // --- Lectura de parámetros de filtro ---
        String q = request.getParameter("q");
        String precioStr = request.getParameter("precio");
        String capacidadStr = request.getParameter("capacidad");

        Double precioMin = null;
        Double precioMax = null;
        Integer capMin = null;
        Integer capMax = null;

        if ("150".equals(precioStr)) precioMax = 150.0;
        else if ("500".equals(precioStr)) precioMax = 500.0;
        else if ("900".equals(precioStr)) precioMax = 900.0;
        else if ("900+".equals(precioStr)) precioMin = 900.0;

        if ("1-50".equals(capacidadStr)) { capMin = 1; capMax = 50; }
        else if ("51-150".equals(capacidadStr)) { capMin = 51; capMax = 150; }
        else if ("151-300".equals(capacidadStr)) { capMin = 151; capMax = 300; }
        else if ("300+".equals(capacidadStr)) { capMin = 300; }

        try {
            List<SalonEventos> catalogo;

            // Si NO hay filtros activos, se ejecuta EXACTAMENTE el código original de tus compañeros
            if ((q == null || q.trim().isEmpty()) && precioStr == null && capacidadStr == null) {
                catalogo = recintoService.obtenerCatalogo(idUsuario);
            } else {
                catalogo = recintoService.obtenerCatalogoFiltrado(q, precioMin, precioMax, capMin, capMax);
            }

            request.setAttribute("catalogo", catalogo);
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Error al cargar los recintos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}