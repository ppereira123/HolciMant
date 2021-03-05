package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;
import java.util.List;

public class RevisionPuntoBloqueo implements Serializable {
    String nombre;
    String fecha;
    String proximaFecha;
    List<PuntoBloqueo> puntos;

    public RevisionPuntoBloqueo() {
    }

    public RevisionPuntoBloqueo(String nombre, String fecha, String proximaFecha, List<PuntoBloqueo> puntos) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.proximaFecha = proximaFecha;
        this.puntos = puntos;
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

    public String getProximaFecha() {
        return proximaFecha;
    }

    public void setProximaFecha(String proximaFecha) {
        this.proximaFecha = proximaFecha;
    }

    public List<PuntoBloqueo> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<PuntoBloqueo> puntos) {
        this.puntos = puntos;
    }
}
