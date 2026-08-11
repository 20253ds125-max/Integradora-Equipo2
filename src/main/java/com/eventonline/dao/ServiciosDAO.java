package com.eventonline.dao;

import com.eventonline.model.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiciosDAO {

    Conexion conexion = new Conexion();

    public boolean registrarServicios(Servicio servicio,int idUsuario)throws SQLException {
        String introduce= "INSERT INTO publicacion_servicio_extra (nombre_servicio,descripcion,precio,url_foto,id_usuario,tipo,ubicacion) VALUES (?,?,?,?,?,?,?)";
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(introduce)){
            ps.setString(1,servicio.getNombreServicio());
            ps.setString(2,servicio.getDescripcion());
            ps.setDouble(3,servicio.getPrecio());
            ps.setString(4,servicio.getUrlFoto());
            ps.setInt(5,idUsuario);
            ps.setString(6,servicio.getTipo());
            ps.setString(7,servicio.getUbicacion());

            return ps.executeUpdate()>0;
        }
    }

    public List<Servicio> buscarPendientes()throws SQLException{
        List <Servicio> pendientes = new ArrayList<>();
        String buscarPendientes="SELECT id_se,nombre_servicio,precio,url_foto FROM publicacion_servicio_extra WHERE estado = 'PENDIENTE'";
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscarPendientes);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                Servicio servicio = new Servicio(
                        rs.getInt("id_se"),
                        rs.getString("nombre_servicio"),
                        rs.getString("url_foto"),
                        rs.getDouble("precio")
                );
                pendientes.add(servicio);
            }

        }
        return pendientes;
    }
    public boolean aceptarSolicitud(int idSE)throws  SQLException{
        String cambiarEstado="Update publicacion_servicio_extra SET estado = 'APROBADO' WHERE id_se = ?";
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(cambiarEstado) ){
            ps.setInt(1,idSE);
            int modificados = ps.executeUpdate();
            return modificados==1;
        }
    }
    public boolean rechazarSolicitud(int idServicio)throws SQLException{
        String eliminar="DELETE FROM publicacion_servicio_extra WHERE id_se = ?";
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(eliminar) ){
                ps.setInt(1,idServicio);
                return ps.executeUpdate()==0;
        }
    }
    public List<Servicio> obtenerCatalogo()throws SQLException{
        String buscarAprobado="Select id_se,nombre_servicio,descripcion,precio,url_foto,tipo,ubicacion FROM publicacion_servicio_extra WHERE UPPER(estado)= 'APROBADO'";
        List<Servicio> catalogo = new ArrayList<>();
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscarAprobado);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                Servicio servicio = new Servicio(
                        rs.getInt("id_se"),
                        rs.getString("nombre_servicio"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("url_foto"),
                        rs.getString("tipo"),
                        rs.getString("ubicacion")
                );
                catalogo.add(servicio);
            }

        }
        return catalogo;
    }
    public Map<String, String> obtenerDatosParaCorreo(int idServicio) throws SQLException {
        String query = "SELECT p.nombre_servicio, p.url_foto, u.correo " +
                "FROM publicacion_servicio_extra p " +
                "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                "WHERE p.id_SE = ?";

        Map<String, String> datos = new HashMap<>();

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    datos.put("nombre", rs.getString("nombre_servicio"));
                    datos.put("imagen", rs.getString("url_foto"));
                    datos.put("correo", rs.getString("correo"));
                }
            }
        }
        return datos;
    }
    public String buscarUrlFoto(int id_Servicios)throws SQLException{
        String obtenerUrl="SELECT url_foto FROM publicacion_servicio_extra WHERE id_se = ?";
        try(Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(obtenerUrl);){
            ps.setInt(1,id_Servicios);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getString("url_foto");
                }
            }
        }
        return null;

    }
    public double obtenerPrecioPorId(int idServicio) throws SQLException {
        String sql = "SELECT PRECIO FROM PUBLICACION_SERVICIO_EXTRA WHERE ID_SE = ?";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("PRECIO");
                }
            }
        }
        return -1.0;
    }

    //Para el buscador
    public List<Servicio> filtrarServicios(String busqueda, String categoria) throws SQLException {
        List<Servicio> lista = new ArrayList<>();

        // Solo traer servicios APROBADOS
        StringBuilder sql = new StringBuilder("SELECT id_se, nombre_servicio, descripcion, precio, url_foto, tipo, ubicacion FROM publicacion_servicio_extra WHERE UPPER(estado) = 'APROBADO'");

        // Si hay texto en el buscador
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append(" AND LOWER(nombre_servicio) LIKE ?");
        }

        // Si seleccionó una categoría (y que no sea 'TODOS)
        if (categoria != null && !categoria.trim().isEmpty() && !categoria.equalsIgnoreCase("Todos")) {
            sql.append(" AND LOWER(tipo) = ?");
        }

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (busqueda != null && !busqueda.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + busqueda.trim().toLowerCase() + "%");
            }
            if (categoria != null && !categoria.trim().isEmpty() && !categoria.equalsIgnoreCase("Todos")) {
                ps.setString(paramIndex++, categoria.trim().toLowerCase());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Servicio servicio = new Servicio(
                            rs.getInt("id_se"),
                            rs.getString("nombre_servicio"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("url_foto"),
                            rs.getString("tipo"),
                            rs.getString("ubicacion")
                    );
                    lista.add(servicio);
                }
            }
        }
        return lista;
    }

}
