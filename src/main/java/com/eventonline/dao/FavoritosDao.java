package com.eventonline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
}
