package com.eventonline.dao;

import com.eventonline.model.ItemCarrito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDao {
    public final Conexion conexion = new Conexion();

    public boolean agregarRecintoACarrito(int idUsuario, int idPublicacion, double precioUnitario) throws SQLException {
        String sql = "INSERT INTO CARRITO (ID_USUARIO, ID_PUBLICACION_EVENTOS, CANTIDAD, PRECIO_UNITARIO) VALUES (?, ?, 1, ?)";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPublicacion);
            ps.setDouble(3, precioUnitario);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean agregarServicioACarrito(int idUsuario, int idServicio, double precioUnitario) throws SQLException {
        String sql = "INSERT INTO CARRITO (ID_USUARIO, ID_SE, PRECIO_UNITARIO) VALUES (?, ?, ?)";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idServicio);
            ps.setDouble(3, precioUnitario);

            return ps.executeUpdate() > 0;
        }
    }

    public List<ItemCarrito> obtenerItemsPorUsuario(int idUsuario) throws SQLException {
        List<ItemCarrito> items = new ArrayList<>();

        String sql = "SELECT c.ID_ITEM_CARRITO, c.ID_PUBLICACION_EVENTOS, c.ID_SE, c.PRECIO_UNITARIO, " +
                "r.NOMBRE_LUGAR AS NOMBRE_RECINTO, r.UBICACION AS UBICACION_RECINTO, r.URL_PORTADA AS FOTO_RECINTO, " +
                "s.NOMBRE_SERVICIO AS NOMBRE_SERVICIO, s.UBICACION AS UBICACION_SERVICIO, s.URL_FOTO AS FOTO_SERVICIO " +
                "FROM CARRITO c " +
                "LEFT JOIN PUBLICACION_SALON_EVENTOS r ON c.ID_PUBLICACION_EVENTOS = r.ID_PUBLICACION_EVENTOS " +
                "LEFT JOIN PUBLICACION_SERVICIO_EXTRA s ON c.ID_SE = s.ID_SE " +
                "WHERE c.ID_USUARIO = ?";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemCarrito item = new ItemCarrito();
                    item.setIdCarrito(rs.getInt("ID_ITEM_CARRITO"));
                    item.setPrecio(rs.getDouble("PRECIO_UNITARIO"));

                    if (rs.getObject("ID_PUBLICACION_EVENTOS") != null) {
                        item.setTipo("RECINTO");
                        item.setIdPublicacionEventos(rs.getInt("ID_PUBLICACION_EVENTOS"));
                        item.setNombre(rs.getString("NOMBRE_RECINTO"));
                        item.setUbicacion(rs.getString("UBICACION_RECINTO"));
                        item.setUrlFoto(rs.getString("FOTO_RECINTO"));
                    } else {
                        item.setTipo("SERVICIO");
                        item.setIdServicioExtra(rs.getInt("ID_SE"));
                        item.setNombre(rs.getString("NOMBRE_SERVICIO"));
                        item.setUbicacion(rs.getString("UBICACION_SERVICIO"));
                        item.setUrlFoto(rs.getString("FOTO_SERVICIO"));
                    }
                    items.add(item);
                }
            }
        }
        return items;
    }

    public boolean eliminarItem(int idCarrito, int idUsuario) throws SQLException {
        String sql = "DELETE FROM CARRITO WHERE ID_ITEM_CARRITO = ? AND ID_USUARIO = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean vaciarCarrito(int idUsuario) throws SQLException {
        String sql = "DELETE FROM CARRITO WHERE ID_USUARIO = ?";
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean existeRecintoEnCarrito(int idUsuario, int idPublicacion) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CARRITO WHERE ID_USUARIO = ? AND ID_PUBLICACION_EVENTOS = ?";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPublicacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

}