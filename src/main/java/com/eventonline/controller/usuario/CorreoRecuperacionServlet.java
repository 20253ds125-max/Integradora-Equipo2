package com.eventonline.controller.usuario;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import com.eventonline.service.UsuarioService;
import com.eventonline.util.CorreoElectronico;
import com.eventonline.util.GeneradorCodigo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/correoRecuperacion")
public class CorreoRecuperacionServlet extends HttpServlet {

    UsuarioService usuarioService = new UsuarioService();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        String correo= request.getParameter("correo");

        try {
            usuarioService.correoRecuperacion(correo);
            request.setAttribute("correo", correo);
            request.getRequestDispatcher("/WEB-INF/codigoVerificacion.jsp").forward(request, response);

        }catch (SQLException e){
            request.setAttribute("error", "Error interno"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/restablecer-correo.jsp").forward(request, response);
        }catch (IllegalArgumentException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/restablecer-correo.jsp").forward(request, response);
        }
    }

}
