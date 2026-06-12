package com.eventonline.model;

import com.eventonline.utils.Alertas;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String direccionFoto;
    private String rol;

    public Usuario(String correo, String nombre, String contrasena, String rol) throws Alertas {
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

    public void setEmail(String email) throws Alertas {
        if(email==null||email.trim().isEmpty()||!email.contains("@")){
            throw new Alertas("Error en el campo de nombre");
        }
        this.email =email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Alertas {
        if(nombre==null||nombre.trim().isEmpty()){
            throw new Alertas("Error en el campo de nombre");
        }
        this.nombre=nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String pass) throws Alertas {
        if(pass==null||pass.trim().isEmpty()){
            throw new Alertas("Error en el campo de contraseña");
        }
        contrasena=pass;
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
