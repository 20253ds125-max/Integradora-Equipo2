package com.eventonline.controller;

import com.eventonline.dao.UsuariosDao;
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

    private final CorreoElectronico correoElectronico= new CorreoElectronico();
    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        String correo= request.getParameter("correo");

        try {
           if(usuariosDao.buscarUsuarioPorCorreo(correo)==null) {
               request.setAttribute("error", "Correo no esta registrado");
               request.getRequestDispatcher("/WEB-INF/restablecer-correo.jsp").forward(request, response);
               return;
           }
           String codigo = GeneradorCodigo.generarCodigo();

           usuariosDao.enviarCodigoVerificacion(correo,codigo);
           boolean correoEnviado = correoElectronico.enviarCodigoVerificacion(correo, codigo);

            if (correoEnviado) {
                request.setAttribute("correo", correo);
                request.getRequestDispatcher("/WEB-INF/codigoVerificacion.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo enviar el correo de verificación. Intenta nuevamente.");
                request.getRequestDispatcher("/WEB-INF/restablecer-correo.jsp").forward(request, response);
            }

        }catch (SQLException e){
            request.setAttribute("error", "Error interno"+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/restablecer-correo.jsp").forward(request, response);
        }
    }

}
