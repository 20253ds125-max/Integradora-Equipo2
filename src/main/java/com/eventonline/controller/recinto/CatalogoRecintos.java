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

@WebServlet("/catalogo")
public class CatalogoRecintos extends HttpServlet {
    public final RecintoService recintoService = new RecintoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<SalonEventos> catalogo = recintoService.obtenerCatalogo();
            request.setAttribute("catalogo",catalogo);
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);
        }catch (SQLException e){
            request.setAttribute("error","erros al cargar los recintos"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);
        }

    }
}
