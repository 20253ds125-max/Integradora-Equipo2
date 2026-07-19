package com.eventonline.model;

import java.util.List;

public class SalonEventos {

    private int idSalonEventos;
    private String nombre;
    private String descripcion;
    private int capacidad;
    private String ubicacion;
    private double precio;
    private List<String> fotos;
    private String fotoPrincipal;

    public SalonEventos(String nombre, String descripcion, int capacidad, String ubicacion, double precio, List<String> fotos) {
        setNombre(nombre);
        setDescripcion(descripcion);
        setCapacidad(capacidad);
        setUbicacion(ubicacion);
        setPrecio(precio);
        setFotos(fotos);
    }

    public int getIdSalonEventos() {
        return idSalonEventos;
    }

    public void setIdSalonEventos(int idSalonEventos) {
        this.idSalonEventos = idSalonEventos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del salón es obligatorio.");
        }
        if (nombre.length() > 150) {
            throw new IllegalArgumentException("El nombre del salón es muy largo.");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (descripcion.length() > 4000) {
            throw new IllegalArgumentException("La descripción es demasiado larga (máximo 4000 caracteres).");
        }
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número mayor a cero.");
        }
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del salón es obligatoria.");
        }
        if (ubicacion.length() > 255) {
            throw new IllegalArgumentException("Número de caracteres superado (255 caracteres máximos).");
        }
        this.ubicacion = ubicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio de renta no puede ser un número negativo.");
        }
        this.precio = precio;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        if (fotos == null || fotos.isEmpty()) {
            throw new IllegalArgumentException("Debes subir al menos una foto de tu salón de eventos.");
        }
        if (fotos.size() > 6) {
            throw new IllegalArgumentException("Máximo puedes subir 6 fotos por salón.");
        }

        this.fotos = fotos;
        this.fotoPrincipal = fotos.getFirst();
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

}
