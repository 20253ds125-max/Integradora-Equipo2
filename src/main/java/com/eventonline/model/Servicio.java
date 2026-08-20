package com.eventonline.model;


public class Servicio {
    private int idServicio;
    private String nombreServicio;
    private String descripcion;

    public Servicio(int idServicio, String nombreServicio, String descripcion, double precio, String urlFoto, String tipo, String ubicacion) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlFoto = urlFoto;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    private double precio;
    private String urlFoto;
    private String tipo;
    private String ubicacion;


    public Servicio() {}

    public Servicio(String nombreServicio, String descripcion, double precio, String tipo,String ubicacion) {
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.ubicacion=ubicacion;
    }

    public Servicio(int idServicio,String nombreServicio,String urlFoto,double precio){
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.urlFoto = urlFoto;
        this.precio = precio;
    }


    public void validarDatosServicio() {
        if (this.nombreServicio == null || this.nombreServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio.");
        }
        if (this.nombreServicio.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre del servicio debe tener al menos 3 caracteres.");
        }
        if (this.nombreServicio.length() > 30) {
            throw new IllegalArgumentException("El nombre del servicio no puede exceder los 30 caracteres.");
        }

        if (this.descripcion == null || this.descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del servicio es obligatoria.");
        }
        if (this.descripcion.trim().length() < 10) {
            throw new IllegalArgumentException("La descripción debe ser más detallada (mínimo 10 caracteres).");
        }
        if (this.descripcion.length() > 100) {
            throw new IllegalArgumentException("La descripción no puede exceder los 100 caracteres.");
        }

        if (this.precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser un valor mayor a $0.00.");
        }
        if (Double.isNaN(this.precio) || Double.isInfinite(this.precio)) {
            throw new IllegalArgumentException("El precio ingresado no es un número válido.");
        }
        if (this.precio > 1000000) {
            throw new IllegalArgumentException("El precio excede el límite máximo permitido.");
        }

        if (this.tipo == null || this.tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de servicio es obligatorio.");
        }
        if (this.tipo.trim().length() < 2) {
            throw new IllegalArgumentException("El tipo de servicio debe tener al menos 3 caracteres.");
        }
        if (this.tipo.length() > 30) {
            throw new IllegalArgumentException("El tipo de servicio no puede exceder los 30 caracteres.");
        }
        if(this.ubicacion.length()>150){
            throw new IllegalArgumentException("la ubicacion de servicio no puede exceder los 150 caracteres.");
        }
        if(this.ubicacion.length()<4){
            throw new IllegalArgumentException("la ubicacion de servico no puede ser menor a los 4 caracteres.");
        }
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
