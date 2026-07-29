package com.eventonline.controller.admin;

import com.eventonline.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AprobarRecintoServlet")
public class AceptarSolicitudServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            int idSalonEventos = Integer.parseInt(request.getParameter("idRecinto"));
            adminService.aceptarSolicitud(idSalonEventos);
            response.sendRedirect(request.getContextPath() + "/adminRecintos");
        }catch (SQLException e){
            request.setAttribute("error","Error al cambiar estado:"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request,response);
        } catch (NumberFormatException e) {
            request.setAttribute("error","Error al cambiar estado:");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request,response);
        }
    }
}
