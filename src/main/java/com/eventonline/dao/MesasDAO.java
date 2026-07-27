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
        }
    }

}
