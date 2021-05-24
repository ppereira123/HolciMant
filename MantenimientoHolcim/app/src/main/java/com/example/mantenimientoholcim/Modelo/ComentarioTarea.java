package com.example.mantenimientoholcim.Modelo;

import java.io.Serializable;

public class ComentarioTarea implements Serializable {
    String comentario;
    String autor;
    String key;

    public ComentarioTarea() {
    }

    public ComentarioTarea(String comentario, String autor, String key) {
        this.comentario = comentario;
        this.autor = autor;
        this.key = key;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ComentarioTarea{" +
                "comentario='" + comentario + '\'' +
                ", autor='" + autor + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
