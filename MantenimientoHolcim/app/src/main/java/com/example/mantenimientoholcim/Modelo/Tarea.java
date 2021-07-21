package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;
import java.util.List;

public class Tarea implements Serializable {
    String codigo;
    String descripcion;
    List<String> encargados;
    String estado;
    List<ComentarioTarea> comentarios;
    String fechadeEnvio;
    String dirImagen;
    String codEquipo;
    String autor;

    public Tarea() {
    }


    public Tarea(String codigo, String descripcion, List<String> encargados, String estado, String fechadeEnvio, String dirImagen, String codEquipo, String autor) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargados = encargados;
        this.estado = estado;
        this.fechadeEnvio = fechadeEnvio;
        this.dirImagen = dirImagen;
        this.codEquipo = codEquipo;
        this.autor = autor;
    }

    public Tarea(String codigo, String descripcion, List<String> encargados, String estado, String fechadeEnvio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargados = encargados;
        this.estado = estado;
        this.fechadeEnvio = fechadeEnvio;
    }

    public Tarea(String codigo, String descripcion, List<String> encargados, String estado, List<ComentarioTarea> comentarios, String fechadeEnvio, String dirImagen, String codEquipo, String autor) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargados = encargados;
        this.estado = estado;
        this.comentarios = comentarios;
        this.fechadeEnvio = fechadeEnvio;
        this.dirImagen = dirImagen;
        this.codEquipo = codEquipo;
        this.autor = autor;
    }

    public Tarea(String codigo, String descripcion, List<String> encargados, String estado, String fechadeEnvio, String codEquipo, String autor) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargados = encargados;
        this.estado = estado;
        this.fechadeEnvio = fechadeEnvio;
        this.codEquipo = codEquipo;
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    public void setDirImagen(String dirImagen) {
        this.dirImagen = dirImagen;
    }

    public String getDirImagen() {
        return dirImagen;
    }

    public String getFechadeEnvio() {
        return fechadeEnvio;
    }

    public void setFechadeEnvio(String fechadeEnvio) {
        this.fechadeEnvio = fechadeEnvio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getEncargados() {
        return encargados;
    }

    public void setEncargados(List<String> encargados) {
        this.encargados = encargados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ComentarioTarea> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioTarea> comentarios) {
        this.comentarios = comentarios;
    }
}
