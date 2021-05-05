package com.example.mantenimientoholcim.Modelo;

public class RealizacionInspeccion {
    String codigo;
    String siguientefecha;

    public RealizacionInspeccion(String codigo, String siguientefecha) {
        this.codigo = codigo;
        this.siguientefecha = siguientefecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSiguientefecha() {
        return siguientefecha;
    }

    public void setSiguientefecha(String siguientefecha) {
        this.siguientefecha = siguientefecha;
    }
}
