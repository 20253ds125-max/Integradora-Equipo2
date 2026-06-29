package com.eventonline.controller;

import com.eventonline.dao.RegistroDAO;
import com.eventonline.model.Usuario;
import com.eventonline.utils.Alertas;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/registro")
public class RegistroUsuarioServlet extends HttpServlet {
    private final RegistroDAO registroDAO = new RegistroDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       ;

        String nombre = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rol = "usuario";

        try {

            Usuario usuario = new Usuario(email,nombre,pass,rol);

            if(registroDAO.registroUsuario(usuario)){
                response.sendRedirect("index.html");
            }

        }catch (Alertas e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request,response);
        }catch (SQLException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request,response);
        }

    }

}
