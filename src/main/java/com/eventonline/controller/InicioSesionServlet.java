package com.eventonline.controller;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
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

    private final UsuariosDao usuariosDao = new UsuariosDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email= request.getParameter("email");
        String pass = request.getParameter("password");

        try{

            Usuario encontrado= usuariosDao.buscarUsuarioPorCorreo(email);
            if(encontrado!=null){
                if(usuariosDao.estadoBloqueado(email)){
                    request.setAttribute("error","Tu cuenta tiene un bloqueo activo intenta mas tarde");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
                    return;
                }
                if(usuariosDao.verificarUsuario(email,pass)!=null){
                    usuariosDao.resetearIntentos(email);
                    HttpSession session = request.getSession();
                    session.setAttribute("UsuarioLog",encontrado);
                    response.sendRedirect(request.getContextPath()+"/index.html");
                }
                else {
                    int intentos = usuariosDao.registrarIntento(email);
                    if(intentos>=3){
                        usuariosDao.bloquearCuenta(email);
                        request.setAttribute("error","Tu cuenta tiene un bloqueo activo, intentalo mas tarde");
                    }else {
                        int intentosRestantes=3-intentos;
                        request.setAttribute("error","Te restan "+intentosRestantes+" intento(s)");
                    }
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
                }
            }else{
                request.setAttribute("error","El correo o la contraseña es incorrecta");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            }
        }catch (SQLException e){
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        }
    }
}
