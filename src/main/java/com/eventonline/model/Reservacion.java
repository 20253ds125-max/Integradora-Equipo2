package com.eventonline.model;

import java.sql.Timestamp;

public class Reservacion {
    private int idReserva;
    private int idUsuario;
    private Integer idPublicacion;
    private String fechaEvento;
    private double total;
    private String estado;
    private Timestamp fechaExpiracion;

    public Reservacion() {}

    // Getters y Setters
    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(Integer idPublicacion) { this.idPublicacion = idPublicacion; }

    public String getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(String fechaEvento) { this.fechaEvento = fechaEvento; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(Timestamp fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}
