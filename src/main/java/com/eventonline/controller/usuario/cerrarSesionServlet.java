package com.eventonline.controller.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cerrarSesion")
public class cerrarSesionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion =req.getSession(false);
        if(sesion!=null){
            sesion.invalidate();
        }
        req.setAttribute("exito","Sesion cerrada");
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req,resp);
    }
}
