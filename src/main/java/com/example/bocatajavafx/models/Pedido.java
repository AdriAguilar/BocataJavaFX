package com.example.bocatajavafx.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "bocadillo_id", nullable = false)
    private Bocadillo bocadillo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Column(name = "coste_total", nullable = false)
    private float costeTotal;

    @Column(nullable = false)
    private String estado;

    public Pedido(int id, Alumno alumno, Bocadillo bocadillo, Date fecha, float costeTotal, String estado) {
        this.id = id;
        this.alumno = alumno;
        this.bocadillo = bocadillo;
        this.fecha = fecha;
        this.costeTotal = costeTotal;
        this.estado = estado;
    }
    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Bocadillo getBocadillo() {
        return bocadillo;
    }

    public Date getFecha() {
        return fecha;
    }

    public float getCosteTotal() {
        return costeTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setBocadillo(Bocadillo bocadillo) {
        this.bocadillo = bocadillo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setCosteTotal(float costeTotal) {
        this.costeTotal = costeTotal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
