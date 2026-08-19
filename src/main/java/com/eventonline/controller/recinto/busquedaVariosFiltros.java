package com.eventonline.controller.recinto;

import com.eventonline.model.SalonEventos;
import com.eventonline.service.RecintoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/busqueda")
public class busquedaVariosFiltros extends HttpServlet {

    private final RecintoService recintoService =  new RecintoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lugar = req.getParameter("lugar");
        String fecha = req.getParameter("fecha");
        String invitados = req.getParameter("invitados");

        try {
            List<SalonEventos> listaFiltros = recintoService.buscarConFiltros(lugar,fecha,invitados);
            req.setAttribute("catalogo",listaFiltros);
        }catch (SQLException e){
            e.printStackTrace();
            req.setAttribute("error","Ocurrio un error al cargar");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error",e.getMessage());
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req,resp);
        }
        req.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(req,resp);

    }
}
