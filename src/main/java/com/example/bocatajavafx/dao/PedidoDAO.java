package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class PedidoDAO {

    public void save(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Pedido> getTodayPedidos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate today = LocalDate.now();

            Query<Pedido> query = session.createQuery("FROM Pedido WHERE fecha = :today", Pedido.class);
            query.setParameter("today", today);

            return query.list();
        }
    }

    public boolean hasOrderedToday(int nia) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate today = LocalDate.now();

            Query<Pedido> query = session.createQuery("FROM Pedido p WHERE p.alumno.nia = :nia AND p.fecha = :today", Pedido.class);
            query.setParameter("nia", nia);
            query.setParameter("today", today);

            return !query.list().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bocadillo getBocadilloByDiaYTipo(Character dia, String tipo, int menu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bocadillo> query = session.createQuery(
                    "FROM Bocadillo WHERE dia = :dia AND tipo = :tipo AND menu = :menu", Bocadillo.class);
            query.setParameter("dia", dia);
            query.setParameter("tipo", tipo);
            query.setParameter("menu", menu);
            return query.uniqueResult();
        }
    }
}
