package com.eventonline.service;

import com.eventonline.dao.ReservacionDao;
import com.eventonline.model.ItemCarrito;
import com.eventonline.model.Reservacion;

import java.sql.SQLException;
import java.util.List;

public class ReservacionService {

    private final ReservacionDao reservacionDao = new ReservacionDao();

    public int procesarBloqueoReserva(int idUsuario, String fechaEvento, double total, List<ItemCarrito> itemsCarrito) throws SQLException {

        if (itemsCarrito == null || itemsCarrito.isEmpty()) {
            throw new IllegalArgumentException("Tu carrito está vacío.");
        }

        ItemCarrito recinto = null;
        int contRecintos = 0;

        for (ItemCarrito item : itemsCarrito) {
            if ("RECINTO".equals(item.getTipo())) {
                recinto = item;
                contRecintos++;
            }
        }

        if (contRecintos > 1) {
            throw new IllegalArgumentException("Solo puedes reservar 1 recinto por evento. Por favor remueve los demás.");
        }

        if (recinto != null) {
            if (reservacionDao.existeReservaRecinto(recinto.getIdPublicacionEventos(), fechaEvento)) {
                throw new IllegalArgumentException("El recinto '" + recinto.getNombre() + "' ya no está disponible para la fecha " + fechaEvento + ".");
            }
        }

        for (ItemCarrito item : itemsCarrito) {
            if ("SERVICIO".equals(item.getTipo())) {
                if (reservacionDao.existeReservaServicio(item.getIdServicioExtra(), fechaEvento)) {
                    throw new IllegalArgumentException("El servicio '" + item.getNombre() + "' no está disponible para la fecha " + fechaEvento + ".");
                }
            }
        }

        Integer idPublicacion = (recinto != null) ? recinto.getIdPublicacionEventos() : null;


        return reservacionDao.crearReservaPendiente(idUsuario, idPublicacion, fechaEvento, total, itemsCarrito);
    }
    public Reservacion obtenerReservaPorId(int idReserva) throws SQLException {
        return reservacionDao.obtenerReservaPorId(idReserva);
    }
    public boolean confirmarPagoReserva(int idReserva) throws SQLException {
        return reservacionDao.confirmarPagoReserva(idReserva);
    }
}