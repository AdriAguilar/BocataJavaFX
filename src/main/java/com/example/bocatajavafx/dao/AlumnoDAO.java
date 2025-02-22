package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class AlumnoDAO {

    public void save(Alumno alumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (alumno.getNia() == 0) {
                session.persist(alumno);
            } else {
                session.merge(alumno);
            }
            transaction.commit();
        }
    }

    public List<Alumno> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Alumno", Alumno.class).list();
        }
    }

    public Alumno getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Alumno.class, id);
        }
    }

    public Alumno getAlumno(String email) {
        Alumno alumno = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Alumno> query = session.createQuery("FROM Alumno WHERE correo = :email", Alumno.class);
            query.setParameter("email", email);

            alumno = query.uniqueResult();
        }
        return alumno;
    }

    public Alumno getAlumnoByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Alumno> query = session.createQuery("FROM Alumno WHERE nombre = :name", Alumno.class);
            query.setParameter("name", name);

            return query.uniqueResult();
        }
    }

    public Alumno getAlumnoPedidos(int nia) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Alumno> query = session.createQuery("FROM Alumno a LEFT JOIN FETCH a.pedidos WHERE a.nia = :nia", Alumno.class);
            query.setParameter("nia", nia);

            return query.uniqueResult();
        }
    }

    public void desactivar(int nia, Date fecha, String motivo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Alumno alumno = session.get(Alumno.class, nia);
            if (alumno != null) {
                alumno.setActivo(false);
                alumno.setFechaBaja(fecha);
                alumno.setMotivoBaja(motivo);
                session.merge(alumno);
            }
            session.getTransaction().commit();
        }
    }

    public void activar(int nia) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Alumno alumno = session.get(Alumno.class, nia);
            if (alumno != null) {
                alumno.setActivo(true);
                alumno.setFechaBaja(null);
                alumno.setMotivoBaja(null);
                session.merge(alumno);
            }
            session.getTransaction().commit();
        }
    }
}
