package com.eventonline.controller;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import com.eventonline.utils.Alertas;
import com.eventonline.utils.Encriptacion;
import com.eventonline.utils.Validaciones;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registro")
public class RegistroUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuariosDao nuevoUsuario = new UsuariosDao();
        Validaciones vali= new Validaciones();

        String nombre = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String rol = "usuario";

        try {
           if(vali.validacionEmail(email)&& vali.validacionPass(pass)&& vali.validacuonNom(nombre)){
               pass= new Encriptacion().Encriptar(pass);
               Usuario usuario = new Usuario(email,nombre,pass,rol);
               nuevoUsuario.registroUsuario(usuario);
           }
        }catch (Alertas e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }




    }

}
