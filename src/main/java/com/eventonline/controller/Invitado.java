
import java.util.ArrayList;
import java.util.List;

class Invitado{
    private String nombre;
    private String apellido;
    private String email;

    public Invitado(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }
    @Override
    public String toString(){
        return "Invitado{"+"nombre="+nombre +
        ", apellido=" + apellido +
        ", email=" + email +'}';
    }
    
}

class Mesas{
    private int numeroMesa;
    private List<Invitado> asientos;

    public Mesas(int numeroMesa, int capacidad){
        this.numeroMesa = numeroMesa;
        this.asientos = new ArrayList<>();

        for(int i = 0; i <capacidad; i++){
            asientos.add(null);
        }
    }

    public void asignarInvitado(int asiento, Invitado invitado){
        if(asiento >= 0 && asiento < asientos.size()){
            asientos.set(asiento, invitado);
            System.out.println("Invitado" + invitado + " asignado al asiento" + asiento + " en la mesa " + numeroMesa);

        }
    }

    public void quitarInvitado(int asiento){
        if(asiento >= 0 && asiento < asientos.size()){
            System.out.println("Invitado" + asientos.get(asiento) + "eliminado de la mesa" + asiento);
            asientos.set(asiento, null);
        }
    }
  
    @Override
public String toString(){
    return "Mesa" + numeroMesa + ":" +asientos;
}
}

class Salon{
    private List<Mesa> mesas;
    private List<Invitado> invitados;

    public Salon(){
        this.mesas = new ArrayList<>();
        this.invitados = new ArrayList<>();
    }
}