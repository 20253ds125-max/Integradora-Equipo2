package com.eventonline.service;

import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.util.CorreoElectronico;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminService {

    private final SalonesDao salonesDao = new SalonesDao();
    private final CorreoElectronico correoElectronico= new CorreoElectronico();
    private final CloudDinary cloudDinary = new CloudDinary();


    public List<SalonEventos> recintosAdmin()throws SQLException {
        return salonesDao.buscarPendientes();
    }

    public void aceptarSolicitud(int idSalonEventos)throws SQLException{
        if(!salonesDao.aceptarSolicitud(idSalonEventos)){
            throw new SQLException("No se puede aprobar: el recinto no existe o ya fue aprobado");
        }
        Map<String, String> datos = salonesDao.obtenerDatosParaCorreo(idSalonEventos);

        if (!datos.isEmpty()) {
            String correoDestino = datos.get("correo");
            String nombreRecinto = datos.get("nombre");
            String urlImagen = datos.get("imagen");

            correoElectronico.enviarAceptacionSolicitud(correoDestino, nombreRecinto, urlImagen);
        }
    }
    public void denegarRecinto(int idSalonEventos)throws SQLException{
        List<String> urls = salonesDao.obtenerUrlFoto(idSalonEventos);
        if(salonesDao.borrarRegistro(idSalonEventos)){
           cloudDinary.borrarFotos(urls);
        }


    }

}
