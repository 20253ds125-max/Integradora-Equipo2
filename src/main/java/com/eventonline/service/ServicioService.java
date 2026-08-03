package com.eventonline.service;

import com.eventonline.dao.ServiciosDAO;
import com.eventonline.model.Servicio;
import com.eventonline.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.sql.SQLException;
import java.util.List;

public class ServicioService {

    private final ServiciosDAO serviciosDAO = new ServiciosDAO();
    private final CloudDinary cloudDinary = new CloudDinary();
    private final Servicio servicio = new Servicio();

    public void anadirServicio(String nombre, String descripcion, String tipo, String precio, Part foto,Usuario usuario,String ubicacion)throws SQLException,IllegalArgumentException,Exception {
        double precioN=0;
        try {
            precioN = Double.valueOf(precio);

            Servicio servicio1 = new Servicio(nombre, descripcion, precioN, tipo,ubicacion);
            servicio1.validarDatosServicio();
            servicio1.setUrlFoto(cloudDinary.subirFoto(foto));
            if (!serviciosDAO.registrarServicios(servicio1, usuario.getIdUsuario())) {
                cloudDinary.borrarFoto(servicio1.getUrlFoto());
                throw new SQLException("Error al registrar tu publicacion");
            }
        }catch (NumberFormatException e ){
            throw new IllegalArgumentException("Precio no puede ser un caracter o cadena de "+e.getMessage());
        }catch (SQLException e){
            cloudDinary.borrarFoto(servicio.getUrlFoto());
            throw new SQLException(e.getMessage());
        }
    }

    public List<Servicio> obtenerCatalogo() throws SQLException{
        System.out.println("2");
        List<Servicio> catalogoServicio = serviciosDAO.obtenerCatalogo();
        return catalogoServicio;
    }
}
