package com.eventonline.service;

import com.eventonline.dao.FavoritosDao;
import com.eventonline.dao.SalonesDao;
import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RecintoService {

    private final SalonesDao salonesDao = new SalonesDao();
    private final FavoritosDao favoritosDao = new FavoritosDao();
    private CloudDinary cloudinaryService = new CloudDinary();

    public void publicarRecinto(String nombre, String ubicacion, String descripcion, String strCapacidad, String strPrecio, HttpServletRequest request, Usuario usuario) throws Exception {
        List<String> rutasFotos = null;
        java.sql.Timestamp fecha = java.sql.Timestamp.valueOf(
                java.time.LocalDateTime.now()
        );
        int capacidad = 0;
        double precio = 0;

        try {
            capacidad = Integer.parseInt(strCapacidad);
            precio = Double.parseDouble(strPrecio);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Campos de capacidad o precio con valores no numericos");
        }
        try {
            rutasFotos = cloudinaryService.subirFotos(request.getParts());
            SalonEventos salonesEventos = new SalonEventos(nombre, descripcion, capacidad, ubicacion, precio, rutasFotos, fecha);
            salonesEventos.validarDatosPublicacion();
            if (!salonesDao.registroSalon(salonesEventos, usuario.getIdUsuario())) {
                throw new IllegalArgumentException("error en la base de datos");
            }
        } catch (Exception e) {
            if (rutasFotos != null && !rutasFotos.isEmpty()) {
                cloudinaryService.borrarFotos(rutasFotos);
            }
            throw e;
        }
    }

    public List<SalonEventos> obtenerCatalogo(Integer idUsuario) throws SQLException {

        List<SalonEventos> catalogo = salonesDao.obtenerCatalogo();

        // Si no hay usuario logueado simplemente regresamos el catalogo
        if (idUsuario == null) {
            return catalogo;
        }

        Set<Integer> favoritos = favoritosDao.obtenerIdsFavoritos(idUsuario);

        for (SalonEventos salon : catalogo) {

            if (favoritos.contains(salon.getIdSalonEventos())) {
                salon.setFavorito(true);
            }

        }

        return catalogo;

    }

    public SalonEventos detallesRecinto(int idRecinto) throws SQLException {
        return salonesDao.obtenerSalon(idRecinto);
    }
    public List<SalonEventos> obtenerCatalogoFiltrado(String busqueda, Double precioMin, Double precioMax, Integer capMin, Integer capMax) throws SQLException {
        return salonesDao.obtenerCatalogoFiltrado(busqueda, precioMin, precioMax, capMin, capMax);
    }
}
