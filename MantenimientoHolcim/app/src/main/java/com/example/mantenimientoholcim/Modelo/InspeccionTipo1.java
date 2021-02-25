package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;
import java.util.HashMap;

public class InspeccionTipo1 implements Serializable {
    String enuunciado;
    String nombreInspector;
    String fechaInspeccion;
    String proximaInspeccion;
    String codigo;
    HashMap<String,String> valores;

    public InspeccionTipo1(String enuunciado, String nombreInspector, String fechaInspeccion, String proximaInspeccion, String codigo, HashMap<String, String> valores) {
        this.enuunciado = enuunciado;
        this.nombreInspector = nombreInspector;
        this.fechaInspeccion = fechaInspeccion;
        this.proximaInspeccion = proximaInspeccion;
        this.codigo = codigo;
        this.valores = valores;
    }

    public InspeccionTipo1(String enuunciado, String nombreInspector, HashMap<String, String> valores) {
        this.enuunciado = enuunciado;
        this.nombreInspector = nombreInspector;
        this.valores = valores;
    }

    public String getEnuunciado() {
        return enuunciado;
    }

    public void setEnuunciado(String enuunciado) {
        this.enuunciado = enuunciado;
    }

    public String getNombreInspector() {
        return nombreInspector;
    }

    public void setNombreInspector(String nombreInspector) {
        this.nombreInspector = nombreInspector;
    }

    public String getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(String fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getProximaInspeccion() {
        return proximaInspeccion;
    }

    public void setProximaInspeccion(String proximaInspeccion) {
        this.proximaInspeccion = proximaInspeccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HashMap<String, String> getValores() {
        return valores;
    }

    public void setValores(HashMap<String, String> valores) {
        this.valores = valores;
    }
}
