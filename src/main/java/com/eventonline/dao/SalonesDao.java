package com.eventonline.dao;

import com.eventonline.model.SalonEventos;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalonesDao {
    private final Conexion conexionConfig = new Conexion();

    public boolean registroSalon(SalonEventos salonesEventos, int idUsuario)throws SQLException {
        String accionSalon = "INSERT INTO publicacion_salon_eventos (nombre_lugar,descripcion,capacidad,ubicacion,precio,url_portada,id_usuario,fecha) VALUES (?,?,?,?,?,?,?,?)";

        String accionFotos = "INSERT INTO fotos (ubicacion,id_salon_eventos) VALUES (?,?)";

        String[] columnasId = {"ID_PUBLICACION_EVENTOS"};

        try (Connection con = conexionConfig.obtenerConexion()) {
            con.setAutoCommit(false);

            try (PreparedStatement psSalon = con.prepareStatement(accionSalon, columnasId)) {

                psSalon.setString(1, salonesEventos.getNombre());
                psSalon.setString(2, salonesEventos.getDescripcion());
                psSalon.setInt(3, salonesEventos.getCapacidad());
                psSalon.setString(4, salonesEventos.getUbicacion());
                psSalon.setDouble(5, salonesEventos.getPrecio());
                psSalon.setString(6, salonesEventos.getFotoPrincipal());
                psSalon.setInt(7, idUsuario);
                psSalon.setTimestamp(8,salonesEventos.getFecha());

                int filassInsertadas = psSalon.executeUpdate();
                if (filassInsertadas == 0) {
                    con.rollback();
                    return false;
                }
                int idGenerado = 0;
                try (ResultSet rs = psSalon.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
                if (idGenerado == 0) {
                    con.rollback();
                    return false;
                }
                if (salonesEventos.getFotos().size() >= 1) {
                    try (PreparedStatement psFotos = con.prepareStatement(accionFotos)) {
                        for (int i = 0; i < salonesEventos.getFotos().size(); i++) {
                            psFotos.setString(1, salonesEventos.getFotos().get(i));
                            psFotos.setInt(2, idGenerado);
                            psFotos.addBatch();
                        }
                        psFotos.executeBatch();
                    }
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }


        }
    }
    public List<SalonEventos> buscarPendientes()throws SQLException{
        List<SalonEventos> salonesPendientes = new ArrayList<>();
        String buscar = "SELECT id_publicacion_eventos,nombre_lugar,ubicacion,url_portada,estado,fecha FROM publicacion_salon_eventos WHERE estado='PENDIENTE'";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscar);
            ResultSet rs = ps.executeQuery() ){
            while (rs.next()){
                SalonEventos salon = new SalonEventos(rs.getInt("id_publicacion_eventos"),
                        rs.getString("nombre_lugar"),
                        rs.getString("ubicacion"),
                        rs.getString("url_portada"),
                        rs.getTimestamp("fecha")
                );
                salonesPendientes.add(salon);
            }
            return salonesPendientes;
        }
    }
    public boolean aceptarSolicitud(int idSalonEventos)throws  SQLException{
        String cambiarEstado="Update publicacion_salon_eventos SET estado = 'APROBADO' WHERE id_publicacion_eventos = ?";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(cambiarEstado) ){
            ps.setInt(1,idSalonEventos);
            int modificados = ps.executeUpdate();
            return modificados==1;
        }
    }
    public List<String> obtenerUrlFoto(int idSalonEventos)throws SQLException{
        List<String>listaUrl= new ArrayList<>();
        String buscarUrl="SELECT ubicacion FROM fotos WHERE id_salon_eventos=?";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscarUrl) ){
            ps.setInt(1,idSalonEventos);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String url=rs.getString("ubicacion");
                listaUrl.add(url);
            }
            return listaUrl;
        }
    }
    public boolean borrarRegistro(int idSalonEventos)throws SQLException{
      String borrarRegistro = "DELETE FROM publicacion_Salon_Eventos WHERE id_publicacion_eventos = ?";

      try (Connection con = conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(borrarRegistro) ){
          ps.setInt(1,idSalonEventos);
          return ps.executeUpdate()>0;

      }
    }

    public Map<String, String> obtenerDatosParaCorreo(int idSalonEventos) throws SQLException {
        String query = "SELECT p.nombre_lugar, p.url_portada, u.correo " +
                "FROM publicacion_salon_eventos p " +
                "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                "WHERE p.id_publicacion_eventos = ?";

        Map<String, String> datos = new HashMap<>();

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idSalonEventos);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    datos.put("nombre", rs.getString("nombre_lugar"));
                    datos.put("imagen", rs.getString("url_portada"));
                    datos.put("correo", rs.getString("correo"));
                }
            }
        }
        return datos;
    }

    public List<SalonEventos> obtenerCatalogo()throws SQLException {

        List<SalonEventos> catalogo = new ArrayList<>();

        String buscarAprobados="Select id_publicacion_eventos,nombre_lugar,ubicacion,capacidad,precio,url_portada FROM publicacion_salon_eventos WHERE upper(estado) = 'APROBADO'";

        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscarAprobados) ){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    SalonEventos salonAprobado= new SalonEventos(
                            rs.getInt("id_publicacion_eventos"),
                            rs.getString("nombre_lugar"),
                            rs.getString("ubicacion"),
                            rs.getInt("capacidad"),
                            rs.getDouble("precio"),
                            rs.getString("url_portada")
                    );
                    catalogo.add(salonAprobado);
                }
            }
        }
        return catalogo;
    }
    public SalonEventos obtenerSalon(int idSalon)throws SQLException{
        String formarDetalles= "SELECT id_publicacion_eventos,nombre_lugar,descripcion, capacidad, ubicacion, precio, id_usuario FROM publicacion_salon_eventos WHERE id_publicacion_eventos = ?";
        String buscarFotos="SELECT ubicacion FROM fotos WHERE id_salon_eventos = ?";
        SalonEventos salonDetalles = null;
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(formarDetalles) ){
            ps.setInt(1,idSalon);
            try(ResultSet rs = ps.executeQuery();){
                if(rs.next()) {
                     salonDetalles = new SalonEventos(
                            rs.getInt("id_publicacion_eventos"),
                            rs.getString("nombre_lugar"),
                            rs.getString("descripcion"),
                            rs.getInt("capacidad"),
                            rs.getString("ubicacion"),
                            rs.getDouble("precio"),
                            rs.getInt("id_usuario"));
                    try (PreparedStatement psFotos = con.prepareStatement(buscarFotos);) {
                        psFotos.setInt(1, idSalon);
                        try (ResultSet rsFotos = psFotos.executeQuery()) {
                            while (rsFotos.next()) {
                                salonDetalles.setFotos(rsFotos.getString("ubicacion"));
                            }

                        }
                    }
                    return salonDetalles;
                }
            }
        }
        return null;
    }

    public List<SalonEventos> obtenerPublicaciones(int idUsuario) throws SQLException {

        List<SalonEventos> publicaciones = new ArrayList<>();

        String consulta = """
            SELECT
                id_publicacion_eventos,
                nombre_lugar,
                ubicacion,
                capacidad,
                precio,
                url_portada
            FROM publicacion_salon_eventos
            WHERE id_usuario = ?
            ORDER BY id_publicacion_eventos DESC
            """;

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SalonEventos salon = new SalonEventos(
                            rs.getInt("id_publicacion_eventos"),
                            rs.getString("nombre_lugar"),
                            rs.getString("ubicacion"),
                            rs.getInt("capacidad"),
                            rs.getDouble("precio"),
                            rs.getString("url_portada")
                    );

                    publicaciones.add(salon);
                }
            }
        }

        return publicaciones;
    }
}
