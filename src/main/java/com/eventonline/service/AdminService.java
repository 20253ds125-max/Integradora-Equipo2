package com.eventonline.service;

import com.eventonline.dao.SalonesDao;
import com.eventonline.dao.ServiciosDAO;
import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Servicio;
import com.eventonline.model.Usuario;
import com.eventonline.util.CorreoElectronico;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminService {

    private final ServiciosDAO serviciosDAO= new ServiciosDAO();
    private final SalonesDao salonesDao = new SalonesDao();
    private final CorreoElectronico correoElectronico= new CorreoElectronico();
    private final UsuariosDao usuariosDao= new UsuariosDao();
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
    public List<Servicio> serviciosAdmin()throws SQLException{
        return serviciosDAO.buscarPendientes();
    }
    public void aceptarSolicitudRecinto(int idServicio)throws SQLException{
        if(!serviciosDAO.aceptarSolicitud(idServicio)){
            throw new SQLException("No se puede aprobar: el recinto no existe o ya fue aprobado");
        }
        Map<String, String> datos = serviciosDAO.obtenerDatosParaCorreo(idServicio);
        if (!datos.isEmpty()) {
            String correoDestino = datos.get("correo");
            String nombreRecinto = datos.get("nombre");
            String urlImagen = datos.get("imagen");

            correoElectronico.enviarAceptacionSolicitud(correoDestino, nombreRecinto, urlImagen);
        }
    }

    public void denegarServicio(int idServicio)throws SQLException {
        String url=serviciosDAO.buscarUrlFoto(idServicio);
        if(serviciosDAO.rechazarSolicitud(idServicio)){
            if(url!=null) {
                cloudDinary.borrarFoto(url);
            }
        }
    }
    public List<Usuario> listaUsuarios()throws SQLException{
        return usuariosDao.listaDeUsuarios();
    }

    public void borrarUsuario(int idUsuario) throws  SQLException{
        if(usuariosDao.buscarAdmin(idUsuario)){
            throw new SQLException("este id no se puede eliminar");
        }else {
            usuariosDao.borrarUsuario(idUsuario);
        }
    }
    public int[] obtenerDatosRecintos()throws SQLException{
        int [] datos = salonesDao.obtenerDatosRecintos();
        return datos;
    }
}
