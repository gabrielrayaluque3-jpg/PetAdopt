package es.iesfranciscodelosrios.petadopt.model;

public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String dni;

    public Persona() {
    }

    public Persona(int id, String nombre, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public abstract String obtenerTipo();
}
