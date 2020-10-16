package com.pablo.surfschools;

/**
 * Created by pablo on 07/06/2017.
 */

public class Individual {
    String nombre,fecha,hora,factura,tipo,descripcion;
    int cantidad,id,aprobado,cancelado,visto;

    public Individual(String nombre, int cantidad, String fecha, String hora, String factura, String tipo, int id, int aprobado, int cancelado, int visto,String descripcion){
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.fecha=fecha;
        this.hora=hora;
        this.factura=factura;
        this.tipo=tipo;
        this.id=id;
        this.aprobado=aprobado;
        this.cancelado=cancelado;
        this.visto=visto;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public int getVisto() {
        return visto;
    }

    public void setVisto(int visto) {
        this.visto = visto;
    }
}
