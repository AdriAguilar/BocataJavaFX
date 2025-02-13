package com.example.bocatajavafx.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String contrasena;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public Usuario(int id, String username, String contrasena, Rol rol) {
        this.id = id;
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public enum Rol {
        cocina,
        administrador
    }
}
