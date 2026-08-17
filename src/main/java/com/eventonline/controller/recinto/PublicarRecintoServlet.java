package com.eventonline.controller.recinto;

import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.CloudDinary;
import com.eventonline.service.RecintoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/publicar-recinto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class    PublicarRecintoServlet extends HttpServlet {
    private final RecintoService recintoService = new RecintoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);

        if(sesion == null|| sesion.getAttribute("UsuarioLog")==null){
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        Usuario usuario= (Usuario) sesion.getAttribute("UsuarioLog");

        String nombre = request.getParameter("venueName");
        String ubicacion= request.getParameter("location");
        String descripcion = request.getParameter("description");
        String strCapacidad= request.getParameter("seated");
        String strPrecio= request.getParameter("precio");


        try{
            recintoService.publicarRecinto(nombre,ubicacion,descripcion,strCapacidad,strPrecio,request,usuario);
            request.setAttribute("exito","Tu peticion de recinto fue enviada");
            request.getRequestDispatcher("/WEB-INF/publicar-recinto.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("error","al subir imagenes: "+e.getMessage());
            request.getRequestDispatcher("/WEB-INF/publicar-recinto.jsp").forward(request,response);
        }

    }
}
