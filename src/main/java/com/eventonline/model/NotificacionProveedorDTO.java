package com.eventonline.model;

public class NotificacionProveedorDTO {
    private String correoDestino;
    private String nombreLugar;
    private String ubicacion;
    private String fotoRecinto;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private String fecha;

    // Constructor vacío
    public NotificacionProveedorDTO() {}

    // Getters y Setters para la fecha que faltaban
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Getters y Setters
    public String getCorreoDestino() { return correoDestino; }
    public void setCorreoDestino(String correoDestino) { this.correoDestino = correoDestino; }

    public String getNombreLugar() { return nombreLugar; }
    public void setNombreLugar(String nombreLugar) { this.nombreLugar = nombreLugar; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getFotoRecinto() { return fotoRecinto; }
    public void setFotoRecinto(String fotoRecinto) { this.fotoRecinto = fotoRecinto; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }
}