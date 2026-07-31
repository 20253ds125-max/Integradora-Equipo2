package com.eventonline.model;

import java.sql.Timestamp;
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
    private java.sql.Timestamp fecha;


    public SalonEventos(){

    }


    public SalonEventos(int idSalonEventos, String nombre,String ubicacion,String fotoPrincipal,java.sql.Timestamp fecha) {
        this.idSalonEventos = idSalonEventos;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fotoPrincipal= fotoPrincipal;
        this.fecha= fecha;
    }

    public SalonEventos(String nombre, String descripcion, int capacidad, String ubicacion, double precio, List<String> fotos, java.sql.Timestamp fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.fotos = fotos;
        if (fotos != null && !fotos.isEmpty()) {
            this.fotoPrincipal = fotos.getFirst();
        }
        this.fecha = fecha;
    }

    public SalonEventos(int idSalonEventos, String nombre,String ubicacion,int capacidad, double precio,String fotoPrincipal ) {
        this.idSalonEventos = idSalonEventos;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.precio = precio;
        this.fotoPrincipal= fotoPrincipal;

    }

    public void validarDatosPublicacion() {
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del salón es obligatorio.");
        }
        if (this.nombre.length() > 150) {
            throw new IllegalArgumentException("El nombre del salón es muy largo.");
        }

        if (this.descripcion == null || this.descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (this.descripcion.length() > 4000) {
            throw new IllegalArgumentException("La descripción es demasiado larga (máximo 4000 caracteres).");
        }

        if (this.capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número mayor a cero.");
        }

        if (this.ubicacion == null || this.ubicacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del salón es obligatoria.");
        }
        if (this.ubicacion.length() > 255) {
            throw new IllegalArgumentException("Número de caracteres superado (255 caracteres máximos).");
        }

        if (this.precio < 0) {
            throw new IllegalArgumentException("El precio de renta no puede ser un número negativo.");
        }

        if (this.fotos == null || this.fotos.isEmpty()) {
            throw new IllegalArgumentException("Debes subir al menos una foto de tu salón de eventos.");
        }
        if (this.fotos.size() > 6) {
            throw new IllegalArgumentException("Máximo puedes subir 6 fotos por salón.");
        }
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
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
        if (fotos != null && !fotos.isEmpty()) {
            this.fotoPrincipal = fotos.getFirst();
        }
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
