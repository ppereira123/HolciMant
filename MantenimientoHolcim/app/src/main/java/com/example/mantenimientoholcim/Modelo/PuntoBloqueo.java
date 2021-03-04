package com.example.mantenimientoholcim.Modelo;

import android.net.Uri;

import com.example.mantenimientoholcim.ElementInspeccion;

import java.util.HashMap;

public class PuntoBloqueo {
    String codigoHac;
    String lugar;
    Uri foto;
    HashMap<String, ElementInspeccion> valores;
    String observaciones;
    String elemento_energia;
    String comparacionenergia;


    public PuntoBloqueo() {
    }

    public PuntoBloqueo(String codigoHac, String lugar, Uri foto, HashMap<String, ElementInspeccion> valores, String observaciones, String elemento_energia, String comparacionenergia) {
        this.codigoHac = codigoHac;
        this.lugar = lugar;
        this.foto = foto;
        this.valores = valores;
        this.observaciones = observaciones;
        this.elemento_energia = elemento_energia;
        this.comparacionenergia = comparacionenergia;
    }

    public String getCodigoHac() {
        return codigoHac;
    }

    public void setCodigoHac(String codigoHac) {
        this.codigoHac = codigoHac;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }

    public HashMap<String, ElementInspeccion> getValores() {
        return valores;
    }

    public void setValores(HashMap<String, ElementInspeccion> valores) {
        this.valores = valores;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getElemento_energia() {
        return elemento_energia;
    }

    public void setElemento_energia(String elemento_energia) {
        this.elemento_energia = elemento_energia;
    }

    public String getComparacionenergia() {
        return comparacionenergia;
    }

    public void setComparacionenergia(String comparacionenergia) {
        this.comparacionenergia = comparacionenergia;
    }
}
