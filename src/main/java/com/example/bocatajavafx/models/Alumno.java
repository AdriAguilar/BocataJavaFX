package com.example.bocatajavafx.models;

import jakarta.persistence.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "alumno")
    private List<Pedido> pedidos;

    public Alumno(int nia, String nombre, String correo, String contrasena) {
        this.nia = nia;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Alumno() {
    }

    public int getNia() {
        return nia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Curso getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
