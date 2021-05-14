package com.example.mantenimientoholcim.Modelo;

public class ElementInspeccion {
    String enunciado;
    String ok;
    String observacion;

    public ElementInspeccion() {
    }

    public ElementInspeccion(String enunciado, String ok, String observacion) {
        this.enunciado = enunciado;
        this.ok = ok;
        this.observacion = observacion;
    }

    public ElementInspeccion(String enunciado, String ok) {
        this.enunciado = enunciado;
        this.ok = ok;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
