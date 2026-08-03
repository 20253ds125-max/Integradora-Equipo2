package com.eventonline.service;

import com.eventonline.dao.FavoritosDao;

import java.sql.SQLException;

public class FavoritosService {

    private final FavoritosDao favoritosDao = new FavoritosDao();

    // Metodo cambiarFavorito
    public void cambiarFavorito(int idUsuario, int idRecinto) throws SQLException {

        if (favoritosDao.esFavorito(idUsuario, idRecinto)) {
            favoritosDao.eliminarFavorito(idUsuario, idRecinto);
        } else {
            favoritosDao.agregarFevorito(idUsuario, idRecinto);
        }
    }
}
