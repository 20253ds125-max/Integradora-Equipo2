package com.eventonline.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/registro")
public class RegistroUsuarioServlet extends HttpServlet {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected void doPost ( HttpServletRequest request, HttpServletResponse response)
        throws  ServletException, IOException {

        response.setContentType("application/json;charset+UTF-8");
        PrintWrite out = response.getWrite();

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String tarjeta  = request.getParameter("tarjeta");
        String foto = request.getParameter("direccionFoto");
        String rol = request.getParameter( "rol");

        if (nombre == null || nombre.isBlank() ||
        correo == null || correo.isBlank() ||
        contrasena == null contrasena.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"campos obligatorios\"}");
            return;
        }

        if (rol == null || rol.isBlank()){
            rol = "planner";
        }

        Usuario usuario = new Usuario (correo, 0, nombre, contrasena,foto != null ? foto: "", rol);
        usuario.setTarjeta(tarjeta != null ? tarjeta: "");

        try {
            boolean exito = usuariosDao.registroUsuario(usuario);
            if (exito){
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"mensaje\":\"usuario registrado correctamente\"}");

            }else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.print("{\"error\":\"No se pudo registrar el usuario, verifique los datos \"}"):

            }
        }catch (SQLException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error de base de datos ")+ e.getMessage().replace("\"", "`") +"\"}");

        }
    }

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        response.sendRedirect(Request.getContextPath() + "/registro.html");

    }

}
