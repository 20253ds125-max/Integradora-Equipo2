package com.eventonline.model;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String rol;
    private String telefono;
    private String ciudad;

    public Usuario(int idUsuario, String nombre, String email, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario(int idUsuario,String correo, String nombre, String rol, String telefono, String ciudad) {
        this.idUsuario=idUsuario;
        this.email = correo;
        this.nombre = nombre;
        this.rol = rol;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public Usuario(String correo, String contrasena) {
        this.email = correo;
        this.contrasena = contrasena;
    }


    public void validarDatosRegistro() {
        if (this.email == null || this.email.trim().isEmpty() || !this.email.contains("@")) {
            throw new IllegalArgumentException("El email es inválido o está vacío.");
        }
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error en el campo de nombre: no puede estar vacío.");
        }
        if (this.contrasena == null || this.contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("Error en el campo de contraseña: no puede estar vacía.");
        }
    }

    public void validarCambioContrasena() {
        if (this.contrasena == null || this.contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("Error en el campo de contraseña: no puede estar vacía.");
        }
    }

    public void validarDatosPerfil() {
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (this.telefono == null || this.telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        if (this.ciudad == null || this.ciudad.trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía.");
        }
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
        this.email = email;
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

    public void setContrasena(String pass) {
        this.contrasena = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}