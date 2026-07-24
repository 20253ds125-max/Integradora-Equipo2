package com.eventonline.controller;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.SQLException;

@WebServlet("/cambiarContra")
public class CambiarContraseñaServlet extends HttpServlet {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String pass= request.getParameter("password");

        try{
            Usuario usuario= new Usuario(correo,pass);
            if(usuariosDao.cambiarContrasena(correo,pass)){
                request.setAttribute("exito", "Contraseña Actualizada");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error en la bd ");
            request.getRequestDispatcher("/WEB-INF/cambiar-contraseña.jsp").forward(request, response);
        }catch (IllegalArgumentException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/cambiar-contraseña.jsp").forward(request, response);

        }
    }
}
