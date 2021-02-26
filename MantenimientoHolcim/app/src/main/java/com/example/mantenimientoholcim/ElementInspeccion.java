package com.example.mantenimientoholcim;

public class ElementInspeccion {
    String enunciado;
    String ok;

    public ElementInspeccion(String enunciado, String ok) {
        this.enunciado = enunciado;
        this.ok = ok;
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
