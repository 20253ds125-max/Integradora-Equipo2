package com.eventonline.controller.admin;

import com.eventonline.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AceptarServicioAdmin")
public class AceptarSolicitudServiciosServlet extends HttpServlet {
    private final AdminService adminService= new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idServicio = Integer.parseInt(request.getParameter("idServicio"));
            adminService.aceptarSolicitudRecinto(idServicio);
            request.setAttribute("exito","Solicitud de servicio aceptada");
            request.getRequestDispatcher("/admin-servicios").forward(request,response);
        }catch (SQLException e){
            e.printStackTrace();
            request.setAttribute("error","Error al cambiar estado:"+e.getMessage());
            request.getRequestDispatcher("/admin-servicios").forward(request,response);
        }catch(NumberFormatException e){
            request.setAttribute("error","Error al cambiar estado:"+e.getMessage());
            request.getRequestDispatcher("/admin-servicios").forward(request,response);
        }
    }
}
