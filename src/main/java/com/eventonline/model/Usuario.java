package com.eventonline.model;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String direccionFoto;
    private String rol;

    public Usuario(String correo, int idUsuario, String nombre, String contrasena, String direccionFoto, String rol) {
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.direccionFoto = direccionFoto;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccionFoto() {
        return direccionFoto;
    }

    public void setDireccionFoto(String direccionFoto) {
        this.direccionFoto = direccionFoto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
