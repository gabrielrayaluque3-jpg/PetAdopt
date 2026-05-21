package es.iesfranciscodelosrios.petadopt.model;

import java.util.Date;

public class Realiza {
    private Date fecha;
    private  boolean completada;
    private Voluntario voluntario;
    private Tarea tarea;

    public Realiza() {}


    public Realiza(Date fecha, boolean completada, Voluntario voluntario, Tarea tarea) {
        this.fecha = fecha;
        this.completada = completada;
        this.voluntario = voluntario;
        this.tarea = tarea;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isCompletada() {
        return completada;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    @Override
    public String  toString() {
        return "AsignacionTarea{" +
                "fecha=" + fecha +
                ", completada=" + completada +
                ", voluntario=" + voluntario +
                ", tarea=" + tarea +
                '}';
    }
}
