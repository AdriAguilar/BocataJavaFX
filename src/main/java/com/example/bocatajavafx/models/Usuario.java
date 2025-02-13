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
    @Column(nullable = false)
    private String rol;

    public Usuario(int id, String username, String contrasena, String rol) {
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

    public String getRol() {
        return rol;
    }
}
