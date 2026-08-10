package com.eventonline.controller.usuario;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import com.eventonline.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet ("/login")
public class InicioSesionServlet extends HttpServlet {

    private final UsuarioService service = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("UsuarioLog") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email= request.getParameter("email");
        String pass = request.getParameter("password");

        try{
            service.iniciarSesion(email,pass,request);

            response.sendRedirect(request.getContextPath()+"/");

        }catch (SQLException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        }catch(IllegalArgumentException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        }
    }
}
