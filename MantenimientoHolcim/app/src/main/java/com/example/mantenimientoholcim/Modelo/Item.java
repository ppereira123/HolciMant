package com.example.mantenimientoholcim.Modelo;

public class Item {
    private String nombres;
    private String codigo;
    private String marca;
    private String descripcion;
    private String observacion;
    private int stock;
    private String estado;
    private String ubicacion;
    private int vidaUtil;
    private String tipoInspeccion;

    public Item(String nombres, String codigo, String marca, String descripcion, String observacion, int stock, String estado, String ubicacion, int vidaUtil, String tipoInspeccion) {
        this.nombres = nombres;
        this.codigo = codigo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.stock = stock;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.vidaUtil = vidaUtil;
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
