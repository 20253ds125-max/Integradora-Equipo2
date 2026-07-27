package com.eventonline.service;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    public void registrarUsuario(Usuario usuario) throws SQLException {

        if (usuariosDao.buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Correo ya existente");
        }

        usuariosDao.guardarUsuario(usuario);
    }
}
