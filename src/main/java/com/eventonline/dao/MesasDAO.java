package com.eventonline.dao;

import com.eventonline.model.Invitados;
import com.eventonline.model.Mesas;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MesasDAO {

    private static final int MAX_INVITADOS_POR_MESA = 10;

    private final Conexion  conexionConfig = new Conexion();

    public List <Mesas> obtenerMesasPorUsuario(int idUsuario) throws SQLException{
        String sqlMesas = "SELECT id_mesa, nombre, capacidad FROM mesas WHERE id_usuario = ? ORDER DY id_mesa";
        String sqlInvitados = "SELECT i.id_invitado, i.nombre, i.correo, i.mesa, i.invitacion_enviada" +
                "FROM invitacion i JOIN mesas m ON i.id_mesa = m.id_mesa" +
                "WHERE m.id_usuario = ? ORDER BY i.id_invitado";
        Map<Integer, Mesa> mesasPorId = new LinkedHashMap<>();

        try(Connection con = conexionConfig.obtenerConexion()){

            try(PreparedStatement ps = con.prepareStatement(sqlMesas)){
                ps.setInt(1, idUsuario);
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
                        Mesa mesa  = new Mesa();
                        mesa.setIdMesa(rs.getInt("id_mesa"));
                        mesa.setNombre(rs.getString("nombre"));
                        mesa.setCapacidad(rs.getInt("capacidad"));
                        mesa.setIdUsuario(idUsuario);
                        mesasPorId.put(mesa.getIdMesa(), mesa);
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInvitados)){
                ps.setInt(1, idUsuario);
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
                        Invitado invitado = new Invitado();
                        invitado.setIdInvitado(rs.getInt("id_invitado"));
                        invitado.setNombre(rs.getString("nombre"));
                        invitado.setCorreo(rs.getString("correo"));
                        invitado.setIdMesa(rs.getInt("id_mesa"));
                        invitado.setInvitacionEnviada("S".equals(rs.getString("invitacion_enviada")));

                        Mesa mesa = mesasPorId.get(invitado.getIdMesa());
                        if (mesa != null) {
                            mesa.getInvitados().add(invitado);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(mesasPorId.values());

    }
    public Mesa crearMesa(Mesa mesa) throws SQLException{
        String sql = "INSERT INTO mesas ( nombre, capacidad, id_usuario) values (?, ?, ?)";
        String[] columnaId = {"ID_MESA"};

        try(Connection con = conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(sql, columnaId)){

            ps.setString(1,mesa.getNombre());
            ps.setInt(2, mesa.getCapacidad());
            ps.setInt(3, mesa.getIdUsuario());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    mesa.setIdMesa(rs.getInt(1));
                }
            }
        }
        return mesa;
    }

}
