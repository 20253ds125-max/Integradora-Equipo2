package com.eventonline.dao;

import com.eventonline.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDao {
    private final Conexion conexionConfig = new Conexion();

    private Usuario armarUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("contrasena"),
                rs.getString("rol")
        );
    }

    public Usuario buscarUsuarioPorCorreo(String correo) throws SQLException {
        String consulta = "SELECT id_usuario, nombre, correo, contrasena, rol FROM usuarios WHERE correo = ?";

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario verificarUsuario(String email, String pass) throws SQLException {
        Usuario usuarioEncontrado = buscarUsuarioPorCorreo(email);

        if (usuarioEncontrado != null) {
            if (BCrypt.checkpw(pass, usuarioEncontrado.getContrasena())) {
                return usuarioEncontrado;
            }
        }

        return null;
    }


    public boolean registroUsuario(Usuario usuario) throws SQLException {
        if (buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            return false;
        }

        String accion = "INSERT INTO usuarios (nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?)";
        String passwordEncrypt = encriptaContrasena(usuario.getContrasena());

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(accion)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, passwordEncrypt);
            ps.setString(4, usuario.getRol());

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    private String encriptaContrasena(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}