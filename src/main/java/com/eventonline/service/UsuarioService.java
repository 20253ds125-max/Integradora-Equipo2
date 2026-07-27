package com.eventonline.service;

import com.eventonline.dao.UsuariosDao;
import com.eventonline.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuariosDao usuariosDao = new UsuariosDao();

    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        return usuariosDao.registroUsuario(usuario);

    }
}
