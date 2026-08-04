package com.eventonline.service;

import com.eventonline.dao.CarritoDao;
import com.eventonline.dao.SalonesDao;
import com.eventonline.dao.ServiciosDAO;

import java.sql.SQLException;

public class CarritoService{
    private final CarritoDao carritoDao = new CarritoDao();
    private final SalonesDao salonesDao = new SalonesDao();
    private final ServiciosDAO serviciosDAO = new ServiciosDAO();

    public boolean agregarRecintoCarrito(int idUsuario, int idPublicacion) throws SQLException {
        double precioUnitario = salonesDao.obtenerPrecioPorId(idPublicacion);

        if (precioUnitario <= 0) {
            throw new SQLException("El precio no es válido.");
        }

        return carritoDao.agregarRecintoACarrito(idUsuario, idPublicacion, precioUnitario);
    }
    public boolean agregarServicioCarrito(int idUsuario, int idServicio) throws SQLException {

        double precioUnitario = serviciosDAO.obtenerPrecioPorId(idServicio);

        if (precioUnitario <= 0) {
            throw new SQLException("El servicio no existe o no tiene un precio válido.");
        }

        return carritoDao.agregarServicioACarrito(idUsuario, idServicio, precioUnitario);
    }
    public boolean eliminarItemCarrito(int idCarrito, int idUsuario) throws SQLException {
        return carritoDao.eliminarItem(idCarrito, idUsuario);
    }
    public boolean vaciarCarrito(int idUsuario) throws SQLException {
        return carritoDao.vaciarCarrito(idUsuario);
    }
}
