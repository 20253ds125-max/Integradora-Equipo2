package com.eventonline.controller;

import com.eventonline.dao.RegistrarSalon;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/publicar-recinto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PublicarRecintoServlet extends HttpServlet {
    private final RegistrarSalon registrarSalon = new RegistrarSalon();
    private final SalonEventos salonEventos = new SalonEventos();

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

        try {
            int capacidad=Integer.parseInt(strCapacidad);
            int precio=Integer.parseInt(strPrecio);
        } catch (NumberFormatException e) {

        }

        java.util.Collection<jakarta.servlet.http.Part> partes = request.getParts();

        for(jakarta.servlet.http.Part parte: partes){
            if(parte.getName().equals("photos")&&parte.getSize()>0){
                String nombreOriginal = parte.getSubmittedFileName();
                long tamanoBytes = parte.getSize();
                String tipoContenido = parte.getContentType();
            }
        }




    }
}
