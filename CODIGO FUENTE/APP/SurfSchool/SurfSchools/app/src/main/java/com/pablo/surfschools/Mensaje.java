package com.pablo.surfschools;

/**
 * Created by pablo on 07/06/2017.
 */

public class Mensaje {
    String contenido,asunto,escuela,fecha;
    int id,visto;
    public Mensaje(String fecha, String contenido, String asunto, String escuela, int visto, int id){
        this.contenido=contenido;
        this.asunto=asunto;
        this.escuela=escuela;
        this.visto=visto;
        this.id=id;
        this.fecha=fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
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

    public int getVisto() {
        return visto;
    }

    public void setVisto(int visto) {
        this.visto = visto;
    }
}
