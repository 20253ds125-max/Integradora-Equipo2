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

    public int registrarIntento(String email)throws SQLException{
        String accionActualizar="UPDATE usuarios SET intentos = intentos + 1 WHERE correo = ?";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionActualizar)){
            ps.setString(1,email);
            ps.executeUpdate();
        }
        String accionBuscar="SELECT intentos FROM usuarios WHERE correo=?";
        try(Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps= con.prepareStatement(accionBuscar)){
            ps.setString(1,email);
            try(ResultSet rs= ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt("intentos");
                }

            }

        }
        return 0;
    }

    public void bloquearCuenta(String email)throws SQLException{
        String accionBloquear="UPDATE usuarios SET tiempo_bloqueado = ? WHERE correo = ?";
        java.sql.Timestamp tiempoBloqueo = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now().plusMinutes(15)
        );
        try(Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionBloquear)){
            ps.setTimestamp(1,tiempoBloqueo);
            ps.setString(2,email);
            ps.executeUpdate();
        }
    }

    public void resetearIntentos(String email)throws SQLException{
        String accionReset="UPDATE usuarios SET intentos = 0,tiempo_bloqueado = NULL WHERE correo = ?";
        try(Connection con = conexionConfig.obtenerConexion();
        PreparedStatement ps= con.prepareStatement(accionReset) ){
            ps.setString(1,email);
            ps.executeUpdate();
        }
    }

    public boolean estadoBloqueado(String email)throws SQLException{
        String accionBuscar="SELECT id_usuario FROM usuarios WHERE correo = ? AND tiempo_bloqueado > SYSTIMESTAMP";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionBuscar)){
            ps.setString(1,email);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }
    public void enviarCodigoVerificacion(String email, String codigo) throws SQLException {
        String actualizarCodigo="UPDATE usuarios SET codigo = ?,tiempo_codigo =? WHERE correo = ?";
        java.sql.Timestamp tiempoCodigo = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now().plusMinutes(15)
        );
        try(Connection con= conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(actualizarCodigo) ){
            ps.setString(1,codigo);
            ps.setTimestamp(2,tiempoCodigo);
            ps.setString(3,email);
            ps.executeQuery();
        }
    }

    public boolean comparaCodigo(String email, String codigo) throws SQLException {
        java.sql.Timestamp tiempoActual = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now().plusMinutes(15)
        );
        String compara="SELECT COUNT(*) FROM usuarios WHERE correo = ? AND codigo = ? AND tiempo_codigo > ? ";
        try(Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(compara) ){
            ps.setString(1,email);
            ps.setString(2,codigo);
            ps.setTimestamp(3,tiempoActual);
            try(ResultSet rs =ps.executeQuery()){
                return rs.getInt(1) ==1;
            }
        }
    }


    private String encriptaContrasena(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}