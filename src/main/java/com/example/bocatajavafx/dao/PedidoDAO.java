package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class PedidoDAO {

    public List<Pedido> getTodayPedidos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate today = LocalDate.now();

            Query<Pedido> query = session.createQuery("FROM Pedido WHERE fecha = :today", Pedido.class);
            query.setParameter("today", today);

            return query.list();
        }
    }
}
