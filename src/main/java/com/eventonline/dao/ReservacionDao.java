package com.eventonline.dao;

import com.eventonline.model.ItemCarrito;
import com.eventonline.model.Reservacion;

import java.sql.*;
import java.util.List;

public class ReservacionDao {
    private final Conexion conexion = new Conexion();

    public boolean existeReservaRecinto(int idPublicacion, String fechaEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM RESERVACION " +
                "WHERE ID_PUBLICACION = ? " +
                "  AND TRUNC(FECHA) = TO_DATE(?, 'YYYY-MM-DD') " +
                "  AND (ESTADO = 'CONFIRMADA' OR (ESTADO = 'PENDIENTE' AND FECHA_EXPIRACION > SYSTIMESTAMP))";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPublicacion);
            ps.setString(2, fechaEvento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existeReservaServicio(int idServicio, String fechaEvento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SERVICIO_EXTRA_RESERVADO sr " +
                "JOIN RESERVACION r ON sr.ID_RESERVA = r.ID_RESERVA " +
                "WHERE sr.ID_SE = ? " +
                "  AND TRUNC(r.FECHA) = TO_DATE(?, 'YYYY-MM-DD') " +
                "  AND (r.ESTADO = 'CONFIRMADA' OR (r.ESTADO = 'PENDIENTE' AND r.FECHA_EXPIRACION > SYSTIMESTAMP))";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);
            ps.setString(2, fechaEvento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int crearReservaPendiente(int idUsuario, Integer idPublicacion, String fechaEvento, double total, List<ItemCarrito> servicios) throws SQLException {

        String sqlReserva = "INSERT INTO RESERVACION (ID_USUARIO, ID_PUBLICACION, FECHA, TOTAL, ESTADO, FECHA_EXPIRACION) " +
                "VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, 'PENDIENTE', SYSTIMESTAMP + INTERVAL '15' MINUTE)";

        String sqlServicio = "INSERT INTO SERVICIO_EXTRA_RESERVADO (COSTO, ID_RESERVA, ID_SE) VALUES (?, ?, ?)";

        Connection con = null;
        PreparedStatement psReserva = null;
        PreparedStatement psServicio = null;
        ResultSet rsKeys = null;
        int idReservaGenerado = 0;

        try {
            con = conexion.obtenerConexion();
            con.setAutoCommit(false);

            psReserva = con.prepareStatement(sqlReserva, new String[]{"ID_RESERVA"});
            psReserva.setInt(1, idUsuario);

            if (idPublicacion != null && idPublicacion > 0) {
                psReserva.setInt(2, idPublicacion);
            } else {
                psReserva.setNull(2, java.sql.Types.INTEGER);
            }

            psReserva.setString(3, fechaEvento);
            psReserva.setDouble(4, total);

            psReserva.executeUpdate();

            rsKeys = psReserva.getGeneratedKeys();
            if (rsKeys.next()) {
                idReservaGenerado = rsKeys.getInt(1);
            }

            if (idReservaGenerado > 0 && servicios != null) {
                psServicio = con.prepareStatement(sqlServicio);
                for (ItemCarrito s : servicios) {
                    if ("SERVICIO".equals(s.getTipo())) {
                        psServicio.setDouble(1, s.getPrecio());
                        psServicio.setInt(2, idReservaGenerado);
                        psServicio.setInt(3, s.getIdServicioExtra());
                        psServicio.addBatch();
                    }
                }
                psServicio.executeBatch();
            }

            con.commit();
            return idReservaGenerado;

        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (rsKeys != null) rsKeys.close();
            if (psReserva != null) psReserva.close();
            if (psServicio != null) psServicio.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public Reservacion obtenerReservaPorId(int idReserva) throws SQLException {
        String sql = "SELECT ID_RESERVA, ID_USUARIO, ID_PUBLICACION, " +
                "TO_CHAR(FECHA, 'YYYY-MM-DD') AS FECHA, TOTAL, ESTADO, FECHA_EXPIRACION " +
                "FROM RESERVACION WHERE ID_RESERVA = ?";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idReserva);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reservacion r = new Reservacion();
                    r.setIdReserva(rs.getInt("ID_RESERVA"));
                    r.setIdUsuario(rs.getInt("ID_USUARIO"));
                    r.setIdPublicacion(rs.getInt("ID_PUBLICACION"));
                    r.setFechaEvento(rs.getString("FECHA"));
                    r.setTotal(rs.getDouble("TOTAL"));
                    r.setEstado(rs.getString("ESTADO"));
                    r.setFechaExpiracion(rs.getTimestamp("FECHA_EXPIRACION"));
                    return r;
                }
            }
        }
        return null;
    }

    public boolean confirmarPagoReserva(int idReserva) throws SQLException {
        String sql = "UPDATE RESERVACION SET ESTADO = 'CONFIRMADA' " +
                "WHERE ID_RESERVA = ? AND ESTADO = 'PENDIENTE' AND FECHA_EXPIRACION > SYSTIMESTAMP";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public List<com.eventonline.model.ReservaConDetalle> obtenerReservasConDetallePorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT r.ID_RESERVA, r.ID_PUBLICACION, s.NOMBRE_LUGAR, s.UBICACION, s.URL_PORTADA, s.CAPACIDAD, " +
                "TO_CHAR(r.FECHA, 'YYYY-MM-DD') AS FECHA, r.TOTAL, r.ESTADO " +
                "FROM RESERVACION r " +
                "LEFT JOIN PUBLICACION_SALON_EVENTOS s ON r.ID_PUBLICACION = s.ID_PUBLICACION_EVENTOS " +
                "WHERE r.ID_USUARIO = ? " +
                "ORDER BY r.ID_RESERVA DESC";
        List<com.eventonline.model.ReservaConDetalle> reservas = new java.util.ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.eventonline.model.ReservaConDetalle r = new com.eventonline.model.ReservaConDetalle();
                    r.setIdReserva(rs.getInt("ID_RESERVA"));

                    int idPublicacion = rs.getInt("ID_PUBLICACION");
                    r.setIdPublicacion(rs.wasNull() ? null : idPublicacion);

                    r.setNombreSalon(rs.getString("NOMBRE_LUGAR"));
                    r.setUbicacion(rs.getString("UBICACION"));
                    r.setUrlPortada(rs.getString("URL_PORTADA"));

                    int capacidad = rs.getInt("CAPACIDAD");
                    r.setCapacidad(rs.wasNull() ? null : capacidad);

                    r.setFechaEvento(rs.getString("FECHA"));
                    r.setTotal(rs.getDouble("TOTAL"));
                    r.setEstado(rs.getString("ESTADO"));
                    reservas.add(r);
                }
            }
        }
        return reservas;
    }

    public com.eventonline.model.ReservaConDetalle obtenerReservaConDetallePorId(int idReserva) throws SQLException {
        String sql = "SELECT r.ID_RESERVA, r.ID_PUBLICACION, s.NOMBRE_LUGAR, s.UBICACION, s.URL_PORTADA, s.CAPACIDAD, " +
                "TO_CHAR(r.FECHA, 'YYYY-MM-DD') AS FECHA, r.TOTAL, r.ESTADO " +
                "FROM RESERVACION r " +
                "LEFT JOIN PUBLICACION_SALON_EVENTOS s ON r.ID_PUBLICACION = s.ID_PUBLICACION_EVENTOS " +
                "WHERE r.ID_RESERVA = ?";

        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                com.eventonline.model.ReservaConDetalle r = new com.eventonline.model.ReservaConDetalle();
                r.setIdReserva(rs.getInt("ID_RESERVA"));

                int idPublicacion = rs.getInt("ID_PUBLICACION");
                r.setIdPublicacion(rs.wasNull() ? null : idPublicacion);

                r.setNombreSalon(rs.getString("NOMBRE_LUGAR"));
                r.setUbicacion(rs.getString("UBICACION"));
                r.setUrlPortada(rs.getString("URL_PORTADA"));

                int capacidad = rs.getInt("CAPACIDAD");
                r.setCapacidad(rs.wasNull() ? null : capacidad);

                r.setFechaEvento(rs.getString("FECHA"));
                r.setTotal(rs.getDouble("TOTAL"));
                r.setEstado(rs.getString("ESTADO"));
                return r;
            }
        }
    }
}



