package com.eventonline.controller;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.utils.Alertas;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet ("/login")
public class LoginServlet extends HttpServlet {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email= request.getParameter("email");
        String pass = request.getParameter("password");

        try{
            Usuario encontrado= usuariosDao.verificarUsuario(email,pass);
            if(encontrado!=null){
                HttpSession session = request.getSession();
                session.setAttribute("UsuarioLog",encontrado);
                response.sendRedirect("index.html");
            }else{
                request.setAttribute("error","El correo o la contraseña es incorrecta");
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
        }catch (SQLException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("alerts.jsp").forward(request,response);
        }
    }
}
