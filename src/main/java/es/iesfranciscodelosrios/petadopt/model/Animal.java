package es.iesfranciscodelosrios.petadopt.model;

public class Animal {
    private int id;
    private String nombre;
    private Especie especie;
    private String raza;
    private int edad;
    private Voluntario voluntario;
    private Adoptante adoptante;

    public Animal() {}


    public Animal(int id, String nombre, Especie especie, String raza, int edad, Voluntario voluntario, Adoptante adoptante) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.voluntario = voluntario;
        this.adoptante = adoptante;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        return edad;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public Adoptante getAdoptante() {
        return adoptante;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public void setAdoptante(Adoptante adoptante) {
        this.adoptante = adoptante;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie=" + especie +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", id_voluntario=" + this.voluntario.getId() +
                ", id_adoptante=" + this.adoptante.getId() +
                '}';
    }
}
