package com.eventonline.service;

import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;

import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private final SalonesDao salonesDao = new SalonesDao();

    public List<SalonEventos> recintosAdmin()throws SQLException {
        return salonesDao.buscarPendientes();
    }
}
