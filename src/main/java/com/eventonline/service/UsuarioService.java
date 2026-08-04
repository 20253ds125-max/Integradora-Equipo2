package com.eventonline.service;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import com.eventonline.util.CorreoElectronico;
import com.eventonline.util.GeneradorCodigo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    public void registrarUsuario(Usuario usuario) throws SQLException {

        if (usuariosDao.buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Correo ya existente");
        }
        usuario.validarDatosRegistro();
        usuariosDao.guardarUsuario(usuario);
    }

    public void iniciarSesion(String email, String pass, HttpServletRequest request)throws SQLException,IllegalArgumentException{
        Usuario encontrado= usuariosDao.buscarUsuarioPorCorreo(email);
        if(encontrado!=null){
            if(usuariosDao.estadoBloqueado(email)){
             throw new IllegalArgumentException("Tu cuenta tiene un bloqueo activo intenta mas tarde");
            }
            if(usuariosDao.verificarUsuario(email,pass)!=null){
                usuariosDao.resetearIntentos(email);
                HttpSession session = request.getSession();
                Usuario usuario = new Usuario(
                        encontrado.getIdUsuario(),
                        encontrado.getEmail(),
                        encontrado.getNombre(),
                        encontrado.getRol()
                );
                session.setAttribute("UsuarioLog",usuario);

            }
            else {
                int intentos = usuariosDao.registrarIntento(email);
                if(intentos>=3){
                    usuariosDao.bloquearCuenta(email);
                    throw new IllegalArgumentException("Tu cuenta tiene un bloqueo activo, intentalo mas tarde");
                }else {
                    int intentosRestantes=3-intentos;
                    throw new IllegalArgumentException("Te restan "+intentosRestantes+" intento(s)");
                }
            }
        }else{
            throw new IllegalArgumentException("El correo o la contraseña es incorrecta");
        }
    }

    public void correoRecuperacion(String correo)throws SQLException,IllegalArgumentException{
        CorreoElectronico correoElectronico= new CorreoElectronico();

        if(usuariosDao.buscarUsuarioPorCorreo(correo)==null) {
            throw new IllegalArgumentException("Correo no esta registrado");
        }
        String codigo = GeneradorCodigo.generarCodigo();

        usuariosDao.enviarCodigoVerificacion(correo,codigo);
        boolean correoEnviado = correoElectronico.enviarCodigoVerificacion(correo, codigo);

        if (!correoEnviado) {
            throw new IllegalArgumentException("No se pudo enviar el correo de verificación. Intenta nuevamente");
        }


    }

    public void verificacionDeCodigo(String correo,String codigoIngresado)throws SQLException,IllegalArgumentException{
        if (!usuariosDao.comparaCodigo(correo, codigoIngresado)) {
            throw new IllegalArgumentException("Codigo incorrecto");
        }
    }
    public void cambiarContrasena(Usuario usuario)throws SQLException,IllegalArgumentException{
        usuario.validarCambioContrasena();
        if(!usuariosDao.cambiarContrasena(usuario.getEmail(), usuario.getContrasena())){
            throw new IllegalArgumentException("Error al cambiar la contraseña");
        }
    }
}
