package com.eventonline.model;

public class Invitados {

    private int idInvitado;
    private String  nombre;
    private String correo;
    private int idMesa;
    private boolean invitacionEnviada;

    public Invitados(){
    }

    public Invitados(String nombre, String correo, int idMesa){
        setNombre(nombre);
        setCorreo(correo);
        this.idMesa = idMesa;
    }

    public int getIdInvitado(){
        return idInvitado;
    }

    public void setIdInvitado(int idInvitado){
        this.idInvitado = idInvitado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        if(nombre == null || nombre.trim().isEmpty()){
            throw  new IllegalArgumentException("El nombre es obligatorio");
        }
        if(nombre.length()> 100){
            throw new IllegalArgumentException("El nombre es demasiado largo");

        }
        this.nombre = nombre.trim();
    }

    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo){
        if(correo == null || correo.trim().isEmpty() || !correo.contains("@")){
            throw new IllegalArgumentException("El correo es invalido");
        }
        if (correo.length()>150){
            throw new IllegalArgumentException("El correo es demasiado largo");
        }
        this.correo = correo.trim();
    }

    public int getIdMesa(){
        return idMesa;
    }

    public void setIdMesa(int idMesa){
        this.idMesa = idMesa;
    }

    public boolean isInvitacionEnviada(){
        return invitacionEnviada;
    }

    public void setInvitacionEnviada(boolean invitacionEnviada){
        this.invitacionEnviada = invitacionEnviada;
    }
}
