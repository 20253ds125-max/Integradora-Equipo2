package com.eventonline.model;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String rol;

    public Usuario(int idUsuario, String nombre, String email, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        setNombre(nombre);
        setEmail(email);
        setContrasena(contrasena);
        setRol(rol);
    }

    public Usuario(String correo, String nombre, String contrasena, String rol) {
        setEmail(correo);
        setNombre(nombre);
        setContrasena(contrasena);
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("El email es inválido o está vacío.");
        }
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error en el campo de nombre: no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String pass) {
        if (pass == null || pass.trim().isEmpty()) {
            throw new IllegalArgumentException("Error en el campo de contraseña: no puede estar vacía.");
        }
        this.contrasena = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}