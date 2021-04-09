package com.example.mantenimientoholcim.Modelo;

import java.util.HashMap;
import java.util.List;

public class Prestamo {
    String id;
    String estado;
    List<HistorialPrestamo> historial;

    public Prestamo(String id, String estado, List<HistorialPrestamo> historial) {
        this.id = id;
        this.estado = estado;
        this.historial = historial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<HistorialPrestamo> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialPrestamo> historial) {
        this.historial = historial;
    }
}
