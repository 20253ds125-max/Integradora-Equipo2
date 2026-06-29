package com.eventonline.controller;

import com.eventonline.dao.Salones;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.CloudDinary;
import com.eventonline.utils.Alertas;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/publicar-recinto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PublicarRecintoServlet extends HttpServlet {
    private final Salones salones = new Salones();
    private CloudDinary cloudinaryService = new CloudDinary();

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



        int capacidad=0;
        double precio = 0;
        try {
            capacidad=Integer.parseInt(strCapacidad);
            precio=Double.parseDouble(strPrecio);
            System.out.println("convertir");
        } catch (NumberFormatException e) {
            request.setAttribute("error","Campos de capacidad o precio con valores no numericos"+e.getMessage());
            request.getRequestDispatcher("publicar-recinto.jsp").forward(request,response);
            return;
        }

        try{
            List<String> rutasFotos=cloudinaryService.subirFotos(request.getParts());
            System.out.println("rutas de fotos");

            SalonEventos salonesEventos =new SalonEventos(nombre,descripcion,capacidad,ubicacion,precio,rutasFotos);

            if(salones.registroSalon(salonesEventos,usuario.getIdUsuario())){
                response.sendRedirect("index.html");
                return;
            }else{
                throw new Alertas("error en la base de datos");
            }
        } catch (Alertas e) {
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("publicar-recinto.jsp").forward(request,response);
        }catch (SQLException e){
            request.setAttribute("error","Error en la base de datos: "+e.getMessage());
            request.getRequestDispatcher("publicar-recinto.jsp").forward(request,response);
        }catch (Exception e){
            request.setAttribute("error","al subir imagenes: "+e.getMessage());
            request.getRequestDispatcher("publicar-recinto.jsp").forward(request,response);
        }

    }
}
