package com.eventonline.dao;

import com.eventonline.model.Invitados;
import com.eventonline.model.Mesas;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MesasDAO {

    public static final int INVITADOS_POR_MESA = 10;

    private final Conexion conexionConfig = new Conexion();


    public static class InfoReserva {
        public final int idUsuario;
        public final String estado;
        public final Integer capacidadSalon;
        public final String nombreSalon;
        public final String ubicacion;
        public final String fechaEvento;

        public InfoReserva(int idUsuario, String estado, Integer capacidadSalon, String nombreSalon,
                           String ubicacion, String fechaEvento) {
            this.idUsuario = idUsuario;
            this.estado = estado;
            this.capacidadSalon = capacidadSalon;
            this.nombreSalon = nombreSalon;
            this.ubicacion = ubicacion;
            this.fechaEvento = fechaEvento;
        }

        /** Maximo de mesas permitido: techo(capacidad / 10). Ej. 115 invitados -> 12 mesas. */
        public int maxMesas() {
            if (capacidadSalon == null || capacidadSalon <= 0) return 0;
            return (int) Math.ceil(capacidadSalon / (double) INVITADOS_POR_MESA);
        }
    }

    public InfoReserva obtenerInfoReserva(int idReserva) throws SQLException {
        String sql = "SELECT r.id_usuario, r.estado, s.capacidad, s.nombre_lugar, s.ubicacion, " +
                "TO_CHAR(r.fecha, 'YYYY-MM-DD') AS fecha_evento " +
                "FROM reservacion r LEFT JOIN publicacion_salon_eventos s ON r.id_publicacion = s.id_publicacion_eventos " +
                "WHERE r.id_reserva = ?";

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                int capacidad = rs.getInt("capacidad");
                Integer capacidadObj = rs.wasNull() ? null : capacidad;

                return new InfoReserva(
                        rs.getInt("id_usuario"),
                        rs.getString("estado"),
                        capacidadObj,
                        rs.getString("nombre_lugar"),
                        rs.getString("ubicacion"),
                        rs.getString("fecha_evento")
                );
            }
        }
    }
    public List<Mesas> obtenerMesasPorReserva(int idReserva) throws SQLException {
        String sqlMesas = "SELECT id_mesa, nombre, capacidad FROM mesas WHERE id_reserva = ? ORDER BY id_mesa";
        String sqlInvitados = "SELECT i.id_invitado, i.nombre, i.correo, i.id_mesa, i.invitacion_enviada " +
                "FROM invitados i JOIN mesas m ON i.id_mesa = m.id_mesa " +
                "WHERE m.id_reserva = ? ORDER BY i.id_invitado";
        Map<Integer, Mesas> mesasPorId = new LinkedHashMap<>();

        try (Connection con = conexionConfig.obtenerConexion()) {

            try (PreparedStatement ps = con.prepareStatement(sqlMesas)) {
                ps.setInt(1, idReserva);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Mesas mesas = new Mesas();
                        mesas.setIdMesa(rs.getInt("id_mesa"));
                        mesas.setNombre(rs.getString("nombre"));
                        mesas.setCapacidad(rs.getInt("capacidad"));
                        mesas.setIdReserva(idReserva);
                        mesasPorId.put(mesas.getIdMesa(), mesas);
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInvitados)) {
                ps.setInt(1, idReserva);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Invitados invitados = new Invitados();
                        invitados.setIdInvitado(rs.getInt("id_invitado"));
                        invitados.setNombre(rs.getString("nombre"));
                        invitados.setCorreo(rs.getString("correo"));
                        invitados.setIdMesa(rs.getInt("id_mesa"));
                        invitados.setInvitacionEnviada("S".equals(rs.getString("invitacion_enviada")));

                        Mesas mesas = mesasPorId.get(invitados.getIdMesa());
                        if (mesas != null) {
                            mesas.getInvitados().add(invitados);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(mesasPorId.values());
    }

    public Mesas crearMesa(Mesas mesa, InfoReserva infoReserva) throws SQLException {
        String sqlContar = "SELECT COUNT(*) FROM mesas WHERE id_reserva = ?";
        String sqlInsert = "INSERT INTO mesas (nombre, capacidad, id_reserva) VALUES (?, ?, ?)";
        String[] columnaId = {"ID_MESA"};

        try (Connection con = conexionConfig.obtenerConexion()) {

            int mesasActuales;
            try (PreparedStatement ps = con.prepareStatement(sqlContar)) {
                ps.setInt(1, mesa.getIdReserva());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    mesasActuales = rs.getInt(1);
                }
            }

            int maxMesas = infoReserva.maxMesas();
            if (maxMesas <= 0) {
                throw new IllegalArgumentException("Esta reserva no tiene un salon asociado con capacidad definida.");
            }
            if (mesasActuales >= maxMesas) {
                throw new IllegalArgumentException("Ya alcanzaste el limite de " + maxMesas +
                        " mesas para este evento (capacidad del salon: " + infoReserva.capacidadSalon + " invitados).");
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInsert, columnaId)) {
                ps.setString(1, mesa.getNombre());
                ps.setInt(2, mesa.getCapacidad());
                ps.setInt(3, mesa.getIdReserva());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        mesa.setIdMesa(rs.getInt(1));
                    }
                }
            }
        }
        return mesa;
    }

    public boolean renombrarMesa(int idMesa, String nuevoNombre, int idReserva) throws SQLException {
        String sql = "UPDATE mesas SET nombre = ? WHERE id_mesa = ? AND id_reserva = ?";

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, idMesa);
            ps.setInt(3, idReserva);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarMesa(int idMesa, int idReserva) throws SQLException {
        String sql = "DELETE FROM mesas WHERE id_mesa = ? AND id_reserva = ?";
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            ps.setInt(2, idReserva);
            return ps.executeUpdate() > 0;
        }
    }

    public Invitados agregarInvitado(Invitados invitados, int idReserva) throws SQLException {
        String sqlValidaMesa = "SELECT capacidad, (SELECT COUNT(*) FROM invitados WHERE id_mesa = m.id_mesa) AS ocupados " +
                "FROM mesas m WHERE m.id_mesa = ? AND m.id_reserva = ?";
        String sqlInsert = "INSERT INTO invitados (nombre, correo, id_mesa) VALUES (?, ?, ?)";
        String[] columnaId = {"ID_INVITADO"};

        try (Connection con = conexionConfig.obtenerConexion()) {
            con.setAutoCommit(false);

            try {
                int capacidad;
                int ocupados;
                try (PreparedStatement ps = con.prepareStatement(sqlValidaMesa)) {
                    ps.setInt(1, invitados.getIdMesa());
                    ps.setInt(2, idReserva);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            throw new IllegalArgumentException("La mesa indicada no existe");
                        }
                        capacidad = rs.getInt("capacidad");
                        ocupados = rs.getInt("ocupados");
                    }
                }

                int limite = Math.min(capacidad, INVITADOS_POR_MESA);
                if (ocupados >= limite) {
                    con.rollback();
                    throw new IllegalArgumentException("La mesa alcanzo su capacidad maxima");
                }
                try (PreparedStatement ps = con.prepareStatement(sqlInsert, columnaId)) {
                    ps.setString(1, invitados.getNombre());
                    ps.setString(2, invitados.getCorreo());
                    ps.setInt(3, invitados.getIdMesa());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            invitados.setIdInvitado(rs.getInt(1));
                        }
                    }
                }
                con.commit();
                return invitados;

            } catch (SQLException | IllegalArgumentException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public boolean eliminarInvitado(int idInvitado, int idReserva) throws SQLException {
        String sql = "DELETE FROM invitados WHERE id_invitado = ? AND id_mesa IN " +
                "(SELECT id_mesa FROM mesas WHERE id_reserva = ?)";
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInvitado);
            ps.setInt(2, idReserva);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Invitados> obtenerInvitadosPendientes(int idReserva) throws SQLException {
        String sql = "SELECT i.id_invitado, i.nombre, i.correo, i.id_mesa " +
                "FROM invitados i JOIN mesas m ON i.id_mesa = m.id_mesa " +
                "WHERE m.id_reserva = ? AND i.invitacion_enviada = 'N'";

        List<Invitados> pendientes = new ArrayList<>();
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invitados invitados = new Invitados();
                    invitados.setIdInvitado(rs.getInt("id_invitado"));
                    invitados.setNombre(rs.getString("nombre"));
                    invitados.setCorreo(rs.getString("correo"));
                    invitados.setIdMesa(rs.getInt("id_mesa"));
                    pendientes.add(invitados);
                }
            }
        }
        return pendientes;
    }

    public void marcarInvitacionEnviada(int idInvitado) throws SQLException {
        String sql = "UPDATE invitados SET invitacion_enviada = 'S', fecha_envio = SYSTIMESTAMP WHERE id_invitado = ?";
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInvitado);
            ps.executeUpdate();
        }
    }

    public String obtenerNombreMesaDeInvitado(int idMesa) throws SQLException {
        String sql = "SELECT nombre FROM mesas WHERE id_mesa = ?";
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("nombre") : "";
            }
        }
    }
}