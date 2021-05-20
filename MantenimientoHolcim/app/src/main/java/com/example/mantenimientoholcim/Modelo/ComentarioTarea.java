package com.example.mantenimientoholcim.Modelo;

public class ComentarioTarea {
    String comentario;
    String fecha;
    String autor;

    public ComentarioTarea() {
    }

    public ComentarioTarea(String comentario, String fecha, String autor) {
        this.comentario = comentario;
        this.fecha = fecha;
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
