package com.eventonline.model;

import java.util.List;
import com.eventonline.utils.Alertas;
public class SalonEventos {

    private int idSalonEventos;
    private String nombre;
    private String descripcion;
    private int capacidad;
    private String ubicacion;
    private double precio;
    private List<String> fotos;
    private String fotoPrincipal;

    public SalonEventos(String nombre,String descripcion,int capacidad,String ubicacion,double precio,List<String> fotos)throws Alertas{
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

    public void setNombre(String nombre) throws Alertas {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Alertas("El nombre del salón es obligatorio.");
        }
        if(nombre.length()>150){throw new Alertas("nombre de salon muy largo.");}
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws Alertas {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Alertas("La descripción no puede estar vacía.");
        }
        if (descripcion.length() > 4000) {
            throw new Alertas("La descripción es demasiado larga (máximo 4000 caracteres).");
        }
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) throws Alertas {
        if (capacidad <= 0) {
            throw new Alertas("La capacidad debe ser un número mayor a cero.");
        }
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) throws Alertas {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new Alertas("La ubicación del salón es obligatoria.");
        }
        if (ubicacion.length()>255){throw new Alertas("numero de caracteres superado (255 caracteres maximos).");}
        this.ubicacion = ubicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws Alertas {
        if (precio < 0) {
            throw new Alertas("El precio de renta no puede ser un número negativo.");
        }
        this.precio = precio;
    }
    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) throws Alertas {
        if (fotos == null || fotos.isEmpty()) {
            throw new Alertas("Debes subir al menos una foto de tu salón de eventos.");
        }
        if (fotos.size() > 5) {
            throw new Alertas("Máximo puedes subir 6 fotos por salón.");
        }

        this.fotos = fotos;

        this.fotoPrincipal=fotos.getFirst();
    }
    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

}
