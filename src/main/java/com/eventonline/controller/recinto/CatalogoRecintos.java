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

        //Parametros de buscador
        String textoBusqueda = request.getParameter("ubicacion");

        double precioMin = parsearDouble(request.getParameter("precioMin"));
        double precioMax = parsearDouble(request.getParameter("precioMax"));
        Integer capacidadMin = parsearEntero(request.getParameter("capacidadMin"));
        Integer capacidadMax = parsearEntero(request.getParameter("capacidadMax"));

        boolean hayFiltros = (textoBusqueda != null && !textoBusqueda.isBlank())
                || precioMin != null || precioMax != null ||capacidadMin != null || capacidadMax != null;

        request.setAttribute("filtroUbicacion", textoBusqueda);
        request.setAttribute("filtroPrecioMin", precioMin);
        request.setAttribute("filtroPrecioMax", precioMax);
        request.setAttribute("filtroCapacidadMin", capacidadMin);
        request.setAttribute("filtroCapacidadMax", capacidadMax);
        try {

            List<SalonEventos> catalogo = hayFiltros
                    ? recintoService.obtenerCatalogo(idUsuario, textoBusqueda, precioMin, precioMax, capacidadMin, capacidadMin)
                        :recintoService.obtenerCatalogo(idUsuario);

            request.setAttribute("catalogo", catalogo);

            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);

        } catch (SQLException e){

            request.setAttribute("error","Error al cargar los recintos: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);

        }
    }

    //Comandos Auxiliares de java
    private Double parsearDouble(String valor){
        if (valor == null || valor.isBlank()) return null;
        try {
            return Double.valueOf(valor);
        }catch (NumberFormatException e){
            return null;
        }
    }

    private Integer parsearEntero(String valor ){
        if (valor == null || valor.isBlank()) return null;
        try {
            return Integer.valueOf(valor);
        }catch (NumberFormatException e ) {
            return null;
        }
    }
}
