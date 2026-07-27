package com.eventonline.controller.usuario;

import com.eventonline.dao.UsuariosDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/verificarCodigo")
public class CodigoVerificacionServlet extends HttpServlet {

    private final UsuariosDao usuariosDao = new UsuariosDao();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");

        String digito1 = request.getParameter("digito1");
        String digito2 = request.getParameter("digito2");
        String digito3 = request.getParameter("digito3");
        String digito4 = request.getParameter("digito4");
        String digito5 = request.getParameter("digito5");
        String digito6 = request.getParameter("digito6");

        String codigoIngresado = digito1 + digito2 + digito3 + digito4 + digito5 + digito6;

        try {
            if (usuariosDao.comparaCodigo(correo, codigoIngresado)) {
                request.setAttribute("correo",correo);
                request.getRequestDispatcher("/WEB-INF/cambiar-contrasena.jsp").forward(request,response);
            }else {
                request.setAttribute("error","Codigo incorrecto");
                request.getRequestDispatcher("/WEB-INF/codigoVerificacion.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "ERROR INTERNO :c "+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/codigoVerificacion.jsp").forward(request, response);
        }



    }
}
