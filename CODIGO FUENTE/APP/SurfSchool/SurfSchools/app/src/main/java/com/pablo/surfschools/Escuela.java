package com.pablo.surfschools;

import java.io.Serializable;

/**
 * Created by Pablo on 03/05/2017.
 */

public class Escuela implements Serializable{
    String latitud;
    String longitud;
    String descripcion;
    String nombre;
    String direccion;
    int posibilidadSurf;

    public  Escuela(String nombre, String descripcion, String latitud, String longitud, String direccion){
        this.nombre=nombre;
        this.longitud=longitud;
        this.latitud=latitud;
        this.descripcion=descripcion;
        this.direccion=direccion;
    }
    public  Escuela(String nombre, String descripcion, String latitud, String longitud, String direccion, int posibilidadSurf){
        this.nombre=nombre;
        this.longitud=longitud;
        this.latitud=latitud;
        this.descripcion=descripcion;
        this.direccion=direccion;
        this.posibilidadSurf=posibilidadSurf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosibilidadSurf() {
        return posibilidadSurf;
    }
}
