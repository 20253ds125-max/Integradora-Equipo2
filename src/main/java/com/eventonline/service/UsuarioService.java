package com.eventonline.service;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    public void registrarUsuario(Usuario usuario) throws SQLException {

        if (usuariosDao.buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Correo ya existente");
        }

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
                session.setAttribute("UsuarioLog",encontrado);

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

    // accesibilidad -> public,private,protected static
}
