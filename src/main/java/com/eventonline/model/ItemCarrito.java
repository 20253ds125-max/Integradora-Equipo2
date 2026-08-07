package com.eventonline.model;

public class ItemCarrito {
    private int idCarrito;
    private Integer idPublicacionEventos;
    private Integer idServicioExtra;
    private String tipo;
    private String nombre;
    private String ubicacion;
    private String urlFoto;
    private double precio;

    public int getIdCarrito() { return idCarrito; }
    public void setIdCarrito(int idCarrito) { this.idCarrito = idCarrito; }

    public Integer getIdPublicacionEventos() { return idPublicacionEventos; }
    public void setIdPublicacionEventos(Integer idPublicacionEventos) { this.idPublicacionEventos = idPublicacionEventos; }

    public Integer getIdServicioExtra() { return idServicioExtra; }
    public void setIdServicioExtra(Integer idServicioExtra) { this.idServicioExtra = idServicioExtra; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
