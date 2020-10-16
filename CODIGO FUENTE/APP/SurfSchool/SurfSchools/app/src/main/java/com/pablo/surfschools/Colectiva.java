package com.pablo.surfschools;

/**
 * Created by pablo on 01/06/2017.
 */

public class Colectiva {
    String precio, descripcion, fecha, nivel,escuela;
    int id;
    public Colectiva(String precio, String descripcion, String fecha, String nivel, int id){
        this.precio=precio;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.nivel=nivel;
        this.id=id;
    }

    public Colectiva(String precio, String descripcion, String fecha, String nivel, int id,String escuela){
        this.precio=precio;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.nivel=nivel;
        this.id=id;
        this.escuela=escuela;
    }


    public String getEscuela() {
        return escuela;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
