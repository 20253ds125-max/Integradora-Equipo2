package com.eventonline.controller.servicios;

import com.eventonline.model.Usuario;
import com.eventonline.service.ServicioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/publicarServicio")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PublicarServicio extends HttpServlet {

    private final ServicioService servicioService = new ServicioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if(sesion == null || sesion.getAttribute("UsuarioLog") == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Usuario usuario = (Usuario) sesion.getAttribute("UsuarioLog");

        String nombreServicio = request.getParameter("nombreServicio");
        String tipo = request.getParameter("tipoServicio");
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");
        String ubicacion = request.getParameter("ubicacionServicio");

        Part fotoUnica = null;
        int cantidadArchivos = 0;

        for (Part part : request.getParts()) {
            if (part.getSubmittedFileName() != null && part.getSize() > 0) {
                cantidadArchivos++;

                if (cantidadArchivos > 1) {
                    break;
                }
                fotoUnica = part;
            }
        }

        if (cantidadArchivos == 0) {
            request.setAttribute("error", "Debes subir una fotografía para el servicio.");
            request.getRequestDispatcher("/WEB-INF/extraServices.jsp").forward(request, response);
            return;
        }

        if (cantidadArchivos > 1) {
            request.setAttribute("error", "Alerta de seguridad: Solo se permite subir (1) una fotografía.");
            request.getRequestDispatcher("/WEB-INF/extraServices.jsp").forward(request, response);
            return;
        }

        try {

            servicioService.anadirServicio(nombreServicio, descripcion, tipo,precio, fotoUnica,usuario,ubicacion);
            request.getRequestDispatcher("/WEB-INF/publicarServicio.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/publicarServicio.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error interno al guardar el servicio. Inténtalo de nuevo más tarde.");
            request.getRequestDispatcher("/WEB-INF/publicarServicio.jsp").forward(request, response);
        } catch (Exception e){
            request.setAttribute("error", "Ocurrió un error interno al guardar el servicio. Inténtalo de nuevo más tarde.");
            request.getRequestDispatcher("/WEB-INF/publicarServicio.jsp").forward(request, response);
        }
    }
}