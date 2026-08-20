package com.eventonline.model;

public class NotificacionDuenoDTO {
    private String correoDestino;
    private String fotoRecinto;
    private String fechaEvento;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;

    // Constructor vacío
    public NotificacionDuenoDTO() {}

    // Getters y Setters
    public String getCorreoDestino() { return correoDestino; }
    public void setCorreoDestino(String correoDestino) { this.correoDestino = correoDestino; }

    public String getFotoRecinto() { return fotoRecinto; }
    public void setFotoRecinto(String fotoRecinto) { this.fotoRecinto = fotoRecinto; }

    public String getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(String fechaEvento) { this.fechaEvento = fechaEvento; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }
}
