package com.eventonline.model;

import java.util.ArrayList;
import java.util.List;

public class Mesas {

    private int idMesa;
    private String nombre;
    private int capacidad;
    private int idReserva;
    private List<Invitados> invitado = new ArrayList<>();

    public Mesas(){

    }
    public Mesas(String nombre, int capacidad, int idReserva){
        setNombre(nombre);
        setCapacidad(capacidad);
        this.idReserva = idReserva;
    }

    public int getIdMesa(){
        return  idMesa;
    }

    public void setIdMesa(int idMesa){
        this.idMesa =idMesa;
    }

    public String getNombre() {
        return  nombre;
    }

    public void setNombre(String nombre){
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la mesa es obligatorio");
        }
        if (nombre.length()>100){
            throw new IllegalArgumentException("El nombre de la mesa es demasiado largo");

        }
        this.nombre = nombre.trim();
    }

    public int getCapacidad(){
        return capacidad;
    }

    public void setCapacidad(int capacidad){
        if(capacidad <=0 || capacidad >10){
            throw  new IllegalArgumentException("Capacidad maxima de 10 personas");
        }
        this.capacidad= capacidad;
    }

    public int getIdReserva(){
        return  idReserva;
    }

    public void setIdReserva(int idReserva){
        this.idReserva = idReserva;
    }

    public List<Invitados> getInvitados(){
        return  invitado;
    }

    public  void setInvitados(List<Invitados> invitado){
        this.invitado = invitado != null ? invitado : new ArrayList<>();
    }

}