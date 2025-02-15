package com.example.bocatajavafx.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    private int id;
    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "curso")
    private List<Alumno> alumnos;

    public Curso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Curso() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
}
