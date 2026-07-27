package com.eventonline.controller.usuario;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/registro")
public class RegistroUsuarioServlet extends HttpServlet {
    private final UsuariosDao usuariosDao = new UsuariosDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rol = "usuario";

        try {

            Usuario usuario = new Usuario(email,nombre,pass,rol);

            usuario.validarDatosRegistro();

            if(usuariosDao.registroUsuario(usuario)){
                request.setAttribute("error","Cuenta creada inicia sesion");
                request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request,response);
            }else{
                throw new IllegalArgumentException("Correo ya existente");
            }

        }catch (IllegalArgumentException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request,response);
        }catch (SQLException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request,response);
        }

    }

}
