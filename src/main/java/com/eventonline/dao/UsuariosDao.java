package com.eventonline.dao;

import com.eventonline.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDao {
    private final Conexion conexionConfig=new Conexion();

    public boolean registroUsuario(Usuario usuario)throws SQLException{
        String accion= "INSERT INTO usuarios (nombre, correo, contrasena,rol) VALUES (?, ?, ?, ?)";

        String passwordEncrypt=encriptaContrasena(usuario.getContrasena());

        try(Connection con=conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accion)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, passwordEncrypt);
            ps.setString(4, usuario.getRol());

            int filasInsertadas= ps.executeUpdate();

            return filasInsertadas>0;
        }

    }
    private String encriptaContrasena(String pass){
        return BCrypt.hashpw(pass,BCrypt.gensalt());
    }

    public Usuario verificarUsuario(String email,String pass)throws SQLException{
        String accion= "SELECT id_usuario, nombre, correo, contrasena, rol FROM usuarios WHERE correo = ?";
        Usuario encontrado = null;

        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accion)){

            ps.setString(1,email);

            try (ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    String encriptada= rs.getString("contrasena");
                    if(BCrypt.checkpw(pass,encriptada)){
                        encontrado = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                encriptada,
                                rs.getString("rol")
                                        );
                    }
                }

            }
        }
    return encontrado;
    }

}