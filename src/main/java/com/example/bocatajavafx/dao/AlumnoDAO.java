package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AlumnoDAO {

    public void save(Alumno alumno) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(alumno);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Alumno> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Alumno", Alumno.class).list();
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
}
