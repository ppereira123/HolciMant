package com.example.mantenimientoholcim.Modelo;

public class RealizacionInspeccion {
    String codigo;
    String siguientefecha;
    String tipoInspeccion;
    String tipo;

    public RealizacionInspeccion() {
    }

    public RealizacionInspeccion(String codigo, String siguientefecha, String tipoInspeccion) {
        this.codigo = codigo;
        this.siguientefecha = siguientefecha;
        this.tipoInspeccion = tipoInspeccion;
    }

    public RealizacionInspeccion(String codigo, String siguientefecha, String tipoInspeccion, String tipo) {
        this.codigo = codigo;
        this.siguientefecha = siguientefecha;
        this.tipoInspeccion = tipoInspeccion;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
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
