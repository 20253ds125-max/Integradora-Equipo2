package com.eventonline.controller.admin;

import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/adminRecintos")
public class RecintosAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List <SalonEventos> listaSalones = new AdminService().recintosAdmin();
            request.setAttribute("salonesPendientes",listaSalones);
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request,response);
        } catch (SQLException e) {
            request.setAttribute("error","Error al cargar los salones de eventos,intenta mas tarde");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        }

    }
}
