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

    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        jakarta.servlet.http.HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
            request.setAttribute("error", "Por favor inicia sesión para continuar.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        com.eventonline.model.Usuario usuarioLogueado = (com.eventonline.model.Usuario) sesion.getAttribute("UsuarioLog");

        if (!"ADMIN".equalsIgnoreCase(usuarioLogueado.getRol())) {
            request.setAttribute("error", "Acceso denegado. Se requiere cuenta de administrador.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        try {
            int[] datos = adminService.obtenerDatosRecintos();
            List <SalonEventos> listaSalones = adminService.recintosAdmin();
            request.setAttribute("datosRecintos",datos);
            request.setAttribute("salonesPendientes",listaSalones);
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request,response);
        } catch (SQLException e) {
            request.setAttribute("error","Error al cargar los salones de eventos,intenta mas tarde");
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
