package com.eventonline.dao;

import com.eventonline.model.SalonEventos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritosDao {

    private final Conexion conexionConfig = new Conexion();

    public boolean esFavorito(int idUsuario, int idRecinto) throws SQLException {

        String consulta = """
                SELECT 1
                FROM favoritos
                WHERE id_usuario = ? AND id_salon_eventos = ?
                """;

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idRecinto);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    public void agregarFevorito(int idUsuario, int idRecinto) throws SQLException {

        String accion = """
                INSERT INTO favoritos (id_usuario, id_salon_eventos)
                VALUES (?, ?)
                """;

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(accion)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idRecinto);

            ps.executeUpdate();
        }

    }

    public void eliminarFavorito(int idUsuario, int idRecinto) throws SQLException {

        String accion = """
                DELETE 
                FROM favoritos 
                WHERE id_usuario = ? AND id_salon_eventos = ?
                """;

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(accion)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idRecinto);

            ps.executeUpdate();
        }
    }

    public Set<Integer> obtenerIdsFavoritos(int idUsuario) throws SQLException {

        Set<Integer> favoritos = new HashSet<>();

        String consulta = """
                SELECT id_salon_eventos
                FROM favoritos
                WHERE id_usuario = ?
                """;

        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    favoritos.add(rs.getInt("id_salon_eventos"));
                }
            }
        }
        return favoritos;
    }

    public List<SalonEventos> obtenerFavoritos (int idUsuario) throws SQLException {

        List<SalonEventos> favoritos = new ArrayList<>();

        String consulta = """
                SELECT
                    p.id_publicacion_eventos,
                    p.nombre_lugar,
                    p.ubicacion,
                    p.capacidad,
                    p.precio,
                    p.url_portada
                FROM favoritos f 
                INNER JOIN publicacion_salon_eventos p
                    ON f.id_salon_eventos = p.id_publicacion_eventos
                WHERE f.id_usuario = ?
                """;

        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SalonEventos salon =
                            new SalonEventos(
                            rs.getInt("id_publicacion_eventos"),
                            rs.getString("nombre_lugar"),
                            rs.getString("ubicacion"),
                            rs.getInt("capacidad"),
                            rs.getDouble("precio"),
                            rs.getString("url_portada")
                    );

                    favoritos.add(salon);

                }
            }
        }

        return favoritos;

    }

}
