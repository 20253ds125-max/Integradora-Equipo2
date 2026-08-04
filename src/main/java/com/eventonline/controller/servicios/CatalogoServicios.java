package com.eventonline.controller.servicios;

import com.eventonline.model.Servicio;
import com.eventonline.service.ServicioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/extraServices")
public class CatalogoServicios extends HttpServlet {
    private final ServicioService servicioService = new ServicioService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            List <Servicio> catalogoServicios = servicioService.obtenerCatalogo();
            request.setAttribute("catalogoServicios",catalogoServicios);
            request.getRequestDispatcher("/WEB-INF/extraServices.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error","error al cargar recintos"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
