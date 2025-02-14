package com.example.bocatajavafx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bocadillos")
public class Bocadillo {
    @Id
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String tipo;
    private String ingredientes;
    @Column(nullable = false)
    private float coste;
    @Column(nullable = false)
    private char dia;
    @Column(nullable = false)
    private int menu;

    public Bocadillo(int id, String nombre, String tipo, String ingredientes, float coste, char dia, int menu) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ingredientes = ingredientes;
        this.coste = coste;
        this.dia = dia;
        this.menu = menu;
    }

    public Bocadillo() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public float getCoste() {
        return coste;
    }

    public char getDia() {
        return dia;
    }

    public int getMenu() {
        return menu;
    }
}
