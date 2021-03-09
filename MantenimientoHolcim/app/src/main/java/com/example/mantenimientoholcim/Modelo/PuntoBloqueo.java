package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;

public class PuntoBloqueo implements Serializable {
    String codigoHac;
    String lugar;
    String rutaimagen;
    String tipoenergia;
    String observaciones;
    String elemento_energia;
    String comparacionenergia;


    public PuntoBloqueo() {
    }

    public PuntoBloqueo(String codigoHac, String lugar, String rutaimagen, String tipoenergia) {
        this.codigoHac = codigoHac;
        this.lugar = lugar;
        this.rutaimagen = rutaimagen;
        this.tipoenergia = tipoenergia;
    }

    public PuntoBloqueo(String codigoHac, String lugar, String rutaimagen, String tipoenergia,  String observaciones, String elemento_energia, String comparacionenergia) {
        this.codigoHac = codigoHac;
        this.lugar = lugar;
        this.rutaimagen = rutaimagen;
        this.tipoenergia = tipoenergia;
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

    public String getRutaimagen() {
        return rutaimagen;
    }

    public void setRutaimagen(String rutaimagen) {
        this.rutaimagen = rutaimagen;
    }

    public String getTipoenergia() {
        return tipoenergia;
    }

    public void setTipoenergia(String tipoenergia) {
        this.tipoenergia = tipoenergia;
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
