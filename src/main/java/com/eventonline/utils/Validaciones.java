package com.eventonline.utils;

public class Validaciones {
    Validaciones vali= new Validaciones();

    public boolean validacionEmail(String email) throws Alertas {
        if(email==null||email.trim().isEmpty()){
            throw new Alertas("Error en el campo de email");
        }
        return true;
    }
    public boolean validacionPass(String pass) throws Alertas {
        if(pass==null||pass.trim().isEmpty()){
            throw new Alertas("Error en el campo de contraseña");
        }
        return true;
    }
    public boolean validacuonNom(String name) throws Alertas {
        if(name==null||name.trim().isEmpty()){
            throw new Alertas("Error en el campo de nombre");
        }
        return true;
    }
    public void notificacion(String notificacion){

    }
}

