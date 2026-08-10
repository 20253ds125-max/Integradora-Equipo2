package com.eventonline.controller.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/mesas")
public class MesasPaginaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null){
            req.setAttribute("error", "Primero inicia sesion para gestionar tus mesas");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/mesas.jsp").forward(req, resp);
    }
}
