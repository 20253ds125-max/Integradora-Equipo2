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


@WebServlet(urlPatterns = {"/detalleRecinto"})
public class DetalleRecinto extends HttpServlet {

    private final RecintoService recintoService = new RecintoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idRecinto= Integer.parseInt(request.getParameter("id"));
            SalonEventos salonDetalles= recintoService.detallesRecinto(idRecinto);
            if(salonDetalles==null){
                throw new SQLException("Error publicacion no encontrada :C");
            }
            request.setAttribute("salonDetalles",salonDetalles);
            request.getRequestDispatcher("/WEB-INF/detalle-recinto.jsp").forward(request,response);
        }catch (NumberFormatException e){
            response.sendRedirect(request.getContextPath()+"/catalogo");
        }catch (SQLException e){
            request.setAttribute("error","erros al cargar el recinto"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request,response);
        }

    }
}
