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
import java.util.List;

@WebServlet("/adminUsuarios")
public class administracionUsuarios extends HttpServlet {
    public final AdminService adminService= new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        try{
            int[] datos= adminService.datosUsuarios();
            List<Usuario> listaDeUsuarios= adminService.listaUsuarios();
            req.setAttribute("datosUsuarios",datos);
            req.setAttribute("listaDeUsuarios",listaDeUsuarios );
            req.getRequestDispatcher("/WEB-INF/usuarios.jsp").forward(req,resp);
    }catch (SQLException e){
            req.setAttribute("error","A ocurrido un error al cargar los usuarios intenta mas tarde: "+e.getMessage() );
            req.getRequestDispatcher("/adminRecintos").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
