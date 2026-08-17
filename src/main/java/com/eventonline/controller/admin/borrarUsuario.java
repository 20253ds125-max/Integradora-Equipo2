package com.eventonline.controller.admin;

import com.eventonline.model.Usuario;
import com.eventonline.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/borrarUsuario")
public class borrarUsuario extends HttpServlet {
    private final AdminService adminService = new AdminService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);

        if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
            req.setAttribute("error", "Por favor inicia sesión para continuar.");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        Usuario usuarioLogueado = (Usuario) sesion.getAttribute("UsuarioLog");

        if (!"ADMIN".equalsIgnoreCase(usuarioLogueado.getRol())) {
            req.setAttribute("error", "Acceso denegado. Se requiere cuenta de administrador.");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }
        try {
            int idUsuario = Integer.parseInt(req.getParameter("usuarioId"));
            adminService.borrarUsuario(idUsuario);
            req.setAttribute("exito","Usuario eliminado");
            req.getRequestDispatcher("/adminUsuarios").forward(req,resp);

        }catch (NumberFormatException e){
            req.setAttribute("error","A ocurrido un error al borrar el usuario intenta mas tarde: "+e.getMessage() );
            req.getRequestDispatcher("/adminUsuarios").forward(req,resp);
        } catch (SQLException e) {
            req.setAttribute("error","A ocurrido un error al borrar el usuario intenta mas tarde: "+e.getMessage() );
            req.getRequestDispatcher("/adminUsuarios").forward(req,resp);
        }
    }
}
