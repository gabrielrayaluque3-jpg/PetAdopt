package es.iesfranciscodelosrios.petadopt.model;

public class AdopcionDetalle {
    private int idAnimal; // Lo guardamos para saber a qué animal quitar el adoptante al eliminar
    private String nombreAdoptante;
    private String telefonoAdoptante;
    private String nombreAnimal;
    private String razaAnimal;

    public AdopcionDetalle(int idAnimal, String nombreAdoptante, String telefonoAdoptante, String nombreAnimal, String razaAnimal) {
        this.idAnimal = idAnimal;
        this.nombreAdoptante = nombreAdoptante;
        this.telefonoAdoptante = telefonoAdoptante;
        this.nombreAnimal = nombreAnimal;
        this.razaAnimal = razaAnimal;
    }

    // Getters y Setters
    public int getIdAnimal() { return idAnimal; }
    public String getNombreAdoptante() { return nombreAdoptante; }
    public String getTelefonoAdoptante() { return telefonoAdoptante; }
    public String getNombreAnimal() { return nombreAnimal; }
    public String getRazaAnimal() { return razaAnimal; }
}