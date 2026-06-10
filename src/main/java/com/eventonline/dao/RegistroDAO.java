package com.eventonline.dao;

import com.eventonline.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroDAO {
    private final Conexion conexionConfig=new Conexion();

    public boolean registroUsuario(Usuario usuario)throws SQLException{
        String accion= "INSERT INTO usuarios (nombre, correo, contrasena,rol) VALUES (?, ?, ?, ?)";

        try(Connection con=conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accion)){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());

            int filasInsertadas= ps.executeUpdate();

            return filasInsertadas>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
