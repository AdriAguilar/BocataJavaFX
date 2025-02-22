package com.example.bocatajavafx.models;

import jakarta.persistence.*;

import java.sql.Date;
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

    private boolean activo;

    @Column(name = "fecha_baja")
    private Date fechaBaja;
    @Column(name = "motivo_baja")
    private String motivoBaja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "alumno")
    private List<Pedido> pedidos;

    public Alumno(int nia, String nombre, String correo, String contrasena, Curso curso) {
        this.nia = nia;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.curso = curso;
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

    public boolean isActivo() {
        return activo;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setFechaBaja(Date fecha_baja) {
        this.fechaBaja = fecha_baja;
    }

    public void setMotivoBaja(String motivo_baja) {
        this.motivoBaja = motivo_baja;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
