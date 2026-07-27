package com.eventonline.dao;

import com.eventonline.model.SalonEventos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonesDao {
    private final Conexion conexionConfig = new Conexion();

    public boolean registroSalon(SalonEventos salonesEventos, int idUsuario)throws SQLException {
        String accionSalon = "INSERT INTO publicacion_salon_eventos (nombre_lugar,descripcion,capacidad,ubicacion,precio,url_portada,id_usuario) VALUES (?,?,?,?,?,?,?)";

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
}
