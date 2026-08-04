package com.eventonline.service;

import com.eventonline.dao.FavoritosDao;
import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class PerfilService {

    private final FavoritosDao favoritosDao = new FavoritosDao();
    private final SalonesDao salonesDao = new SalonesDao();

    public List<SalonEventos> obtenerFavoritos (int idUsuario) throws SQLException {

        return favoritosDao.obtenerFavoritos(idUsuario);

    }

    public List<SalonEventos> obtenerPublicaciones(int idUsuario) throws SQLException {

        return salonesDao.obtenerPublicaciones(idUsuario);

    }

}
