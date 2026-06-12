package com.eventonline.model.user;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet ("/login");
public class LoginServlet extends HttpServlet{

    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected  void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        response.setContentType ("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (correo == null || correo.isBlank () || contrasena == null || contrasena.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Correo y contrsena son obligatorias.\"}");
            return;
        }

        try {
            Usuario usuario = usuariosDao.buscarPorCredenciales(correo, contrasena);
            if (usuario == null) {
                response.setStatus(HtttpSeveletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\":\"Correo o contrasenia incorrectos \"}");
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            session.setMaxInactiveInterval(60 * 60);

            response.setStatus(HttpServeletResponse.SC_OK);
            out.print ("{\"mensaje\":\"Sesion iniciada\","
                    +"\"nombre\":\"" + usuario.getNombre() + "\","
                    + "\"rol\":\"" + usuario.getRol () + "\"}");

        }catch (Exception e ) {
            response.setStatus(HttpServeletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error del servidor\"}");
        }
    }

    @Override
    protected void doGet (HttpServeletRequeste requeste, HttpServeletResponse response)
        throws ServeletException, IOException {
        response.sendRedirect (requeste.getContextPath() + "/login.html");
    }



}

