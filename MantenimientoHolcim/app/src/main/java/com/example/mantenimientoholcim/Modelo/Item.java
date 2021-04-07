package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;

public class Item implements Serializable {
    private String codigo;
    private String marca;
    private String descripcion;
    private String observacion;
    private int stock;
    private int stockDisponible;

    private String ubicacion;
    private int vidaUtil;
    private String tipoInspeccion;
    private String estante;


    public Item() {
    }

    public Item(String codigo, String marca, String descripcion, String observacion, int stock, int stockDisponible, String ubicacion, int vidaUtil, String tipoInspeccion, String estante) {
        this.codigo = codigo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.stock = stock;
        this.stockDisponible = stockDisponible;
        this.ubicacion = ubicacion;
        this.vidaUtil = vidaUtil;
        this.tipoInspeccion = tipoInspeccion;
        this.estante = estante;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }
}
