package com.eventonline.dao;

import com.eventonline.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuariosDao {
    private final Conexion conexionConfig=new Conexion();

    public boolean registroUsuario(Usuario usuario)throws SQLException{
        String accion= "INSERT INTO usuarios (nombre, correo, contraseña, tarjeta, direccion_foto, rol) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection con=conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accion)){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setString(5, usuario.getDireccionFoto());
            ps.setString(6, usuario.getRol());

            int filasInsertadas= ps.executeUpdate();

            return filasInsertadas>0;
        }catch (SQLException e){
            return false;
        }

    }
}
