package es.iesfranciscodelosrios.petadopt.model;

public class Adoptante extends Persona{
    private String telefono;
    private String email;

    public Adoptante() { super(); }

    public Adoptante(int id, String nombre, String dni, String telefono, String email) {
        super(id, nombre, dni);
        this.email = email;
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String obtenerTipo() {
        return "Contacto: "+"\n" +
                "Telefono: "+telefono+"\n" +
                "Email: "+email;
    }

    @Override
    public String toString() {
        return "Adoptante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                obtenerTipo()+
                '}';
    }
}
