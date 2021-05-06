package com.example.mantenimientoholcim.Modelo;

public class PendientesInspeciones {
    String estadoAlarma;
    String codigo;
    String tipoInspeccion;
    String fecha;

    public PendientesInspeciones(String estadoAlarma, String codigo, String tipoInspeccion, String fecha) {
        this.estadoAlarma = estadoAlarma;
        this.codigo = codigo;
        this.tipoInspeccion = tipoInspeccion;
        this.fecha = fecha;
    }

    public String getEstadoAlarma() {
        return estadoAlarma;
    }

    public void setEstadoAlarma(String estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
