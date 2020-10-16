package com.pablo.surfschools;

/**
 * Created by Pablo on 24/05/2017.
 */

public class Excursion {

    int id,participantes;
    String nombre,fecha,descripcion,escuela;
    public Excursion (String nombre, String fecha, String descripcion, int id, int participantes){
        this.nombre=nombre;
        this.fecha=fecha;
        this.descripcion=descripcion;
        this.id=id;
        this.participantes=participantes;
    }

    public Excursion (String nombre, String fecha, String descripcion, int id, int participantes,String escuela){
        this.nombre=nombre;
        this.fecha=fecha;
        this.descripcion=descripcion;
        this.id=id;
        this.participantes=participantes;
        this.escuela=escuela;
    }


    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
