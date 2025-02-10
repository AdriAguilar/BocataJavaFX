package com.example.bocatajavafx.models;

import jakarta.persistence.*;

@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    private int nia;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private String contrasena;

    public Alumno(int nia, String nombre, String correo, String contrasena) {
        this.nia = nia;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Alumno() {
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
