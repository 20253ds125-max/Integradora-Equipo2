package com.eventonline.dao;

import com.eventonline.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDao {

    private final Conexion conexionConfig = new Conexion();

    private Usuario armarUsuario (ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario (
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("contrasena"),
                rs.getString("rol")
        );

        usuario.setTelefono(rs.getString("telefono"));
        usuario.setCiudad(rs.getString("ciudad"));

        return usuario;

    }

    public Usuario buscarUsuarioPorCorreo (String correo) throws SQLException {

        String consulta = "SELECT id_usuario, nombre, correo, contrasena, rol, telefono, ciudad FROM usuarios WHERE correo = ?";

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

    public Usuario verificarUsuario (String email, String pass) throws SQLException {

        Usuario usuarioEncontrado = buscarUsuarioPorCorreo(email);

        if (usuarioEncontrado != null) {
            if (BCrypt.checkpw(pass, usuarioEncontrado.getContrasena())) {
                return usuarioEncontrado;
            }
        }
        return null;
    }

    public boolean guardarUsuario (Usuario usuario) throws SQLException {

        String accion = """
        INSERT INTO usuarios 
            (nombre, correo, contrasena, rol, telefono, ciudad) 
        VALUES (?, ?, ?, ?, ?, ?) 
        """;

        String passwordEncrypt = encriptaContrasena(usuario.getContrasena());

        try (Connection con = conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(accion)){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, passwordEncrypt);
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getCiudad());

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    public int registrarIntento (String email) throws SQLException {

        String accionActualizar="UPDATE usuarios SET intentos = intentos + 1 WHERE correo = ?";

        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionActualizar)) {

            ps.setString(1,email);
            ps.executeUpdate();

        }

        String accionBuscar="SELECT intentos FROM usuarios WHERE correo=?";

        try (Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps= con.prepareStatement(accionBuscar)) {

            ps.setString(1,email);

            try (ResultSet rs= ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("intentos");
                }
            }
        }
        return 0;
    }

    public void bloquearCuenta (String email) throws SQLException {

        String accionBloquear="UPDATE usuarios SET tiempo_bloqueado = ? WHERE correo = ?";

        java.sql.Timestamp tiempoBloqueo = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now().plusMinutes(15)
        );

        try (Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionBloquear)) {
            ps.setTimestamp(1,tiempoBloqueo);
            ps.setString(2,email);
            ps.executeUpdate();
        }
    }

    public void resetearIntentos (String email) throws SQLException {

        String accionReset = "UPDATE usuarios SET intentos = 0,tiempo_bloqueado = NULL WHERE correo = ?";

        try (Connection con = conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(accionReset)) {
            ps.setString(1,email);
            ps.executeUpdate();
        }
    }

    public boolean estadoBloqueado (String email) throws SQLException {

        String accionBuscar = "SELECT id_usuario FROM usuarios WHERE correo = ? AND tiempo_bloqueado > SYSTIMESTAMP";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionBuscar)) {
            ps.setString(1,email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void enviarCodigoVerificacion (String email, String codigo) throws SQLException {

        String actualizarCodigo="UPDATE usuarios SET codigo = ?,tiempo_codigo =? WHERE correo = ?";

        java.sql.Timestamp tiempoCodigo = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now().plusMinutes(15)
        );

        try (Connection con= conexionConfig.obtenerConexion();
        PreparedStatement ps = con.prepareStatement(actualizarCodigo)) {
            ps.setString(1,codigo);
            ps.setTimestamp(2,tiempoCodigo);
            ps.setString(3,email);
            ps.executeUpdate();
        }
    }

    public boolean comparaCodigo (String email, String codigo) throws SQLException {

        java.sql.Timestamp tiempoActual = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now()
        );

        String compara="SELECT COUNT(*) FROM usuarios WHERE correo = ? AND codigo = ? AND tiempo_codigo > ? ";

        try (Connection con= conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(compara)) {

            ps.setString(1,email);
            ps.setString(2,codigo);
            ps.setTimestamp(3,tiempoActual);
            try(ResultSet rs =ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1)>0;
                }
            }
        }
        return false;
    }

    public boolean cambiarContrasena (String email,String contrasena) throws SQLException {

        String accionCambiarContra="UPDATE usuarios SET contrasena = ? WHERE correo = ?";
        String encryptPass = encriptaContrasena(contrasena);

        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(accionCambiarContra)) {

            ps.setString(1,encryptPass);
            ps.setString(2,email);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas == 1;
        }
    }

    private String encriptaContrasena (String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public boolean actualizarPerfil(Usuario usuario) throws SQLException {

        String accion = """
            UPDATE usuarios
            SET nombre = ?,
                telefono = ?,
                ciudad = ?
            WHERE id_usuario = ?
            """;

        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(accion)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getTelefono());
            ps.setString(3, usuario.getCiudad());
            ps.setInt(4, usuario.getIdUsuario());

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;
        }
    }

    public List<Usuario> listaDeUsuarios()throws SQLException{
        List<Usuario> listaDeUsuario = new ArrayList<>();
        String buscarUsuarios="SELECT id_usuario,correo,ciudad,nombre FROM USUARIOS WHERE ROL <> 'ADMIN'";
        try(Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscarUsuarios) ){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Usuario usuario =new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("ciudad")
                    );
                    listaDeUsuario.add(usuario);
                }
                return listaDeUsuario;
            }

        }
    }
    public boolean buscarAdmin(int idUsuario)throws SQLException{
        String buscaAdmin="SELECT COUNT(*) FROM usuarios WHERE id_usuario=? and rol='ADMIN'";
        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(buscaAdmin) ){
            ps.setInt(1,idUsuario);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1)>0;
                }
            }

        }
        return false;
    }
    public void borrarUsuario(int idUsuario)throws SQLException{
        String borrarUsuario="DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection con = conexionConfig.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(borrarUsuario) ){
            ps.setInt(1,idUsuario);
            ps.executeUpdate();
        }
    }
    public int[] obtenerDatosUsuarios()throws SQLException{
        int[] datos = new int[3];

        String sql = "SELECT " +
                "  (SELECT COUNT(*) FROM USUARIOS) AS total, " +
                "  (SELECT COUNT(DISTINCT id_usuario) FROM (" +
                "      SELECT id_usuario FROM PUBLICACION_SALON_EVENTOS " +
                "      UNION " +
                "      SELECT id_usuario FROM PUBLICACION_SERVICIO_EXTRA" +
                "  )) AS con_publicacion, " +
                "  (SELECT COUNT(*) FROM USUARIOS WHERE UPPER(rol) LIKE '%ADMIN%') AS administradores " +
                "FROM DUAL";
        try (Connection con = conexionConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                datos[0] = rs.getInt("total");
                datos[1] = rs.getInt("con_publicacion");
                datos[2] = rs.getInt("administradores");
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw e;
        }

        return datos;
    }
}