package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BocadilloDAO {

    public List<Bocadillo> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bocadillo", Bocadillo.class).list();
        }
    }

    public Bocadillo getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bocadillo.class, id);
        }
    }

    public void save(Bocadillo bocadillo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (bocadillo.getId() == 0) {
                session.persist(bocadillo);
            } else {
                session.merge(bocadillo);
            }
            transaction.commit();
        }
    }

    public void delete(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Bocadillo bocadillo = session.get(Bocadillo.class, id);
            if (bocadillo != null) {
                session.remove(bocadillo);
            }
            transaction.commit();
        }
    }
}
