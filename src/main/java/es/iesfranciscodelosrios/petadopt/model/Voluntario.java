package es.iesfranciscodelosrios.petadopt.model;

public class Voluntario extends Persona{
    private Especialidad especialidad;

    public Voluntario() { super(); }

    public Voluntario(int id, String nombre, String dni, Especialidad especialidad) {
        super(id, nombre, dni);
        this.especialidad = especialidad;
    }

    @Override
    public String obtenerTipo() {
        return "Especialidad: "+especialidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Voluntario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                obtenerTipo() +
                ", dni='" + dni + '\'' +
                '}';
    }
}
