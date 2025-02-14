package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class BocadilloDAO {

    public List<Bocadillo> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bocadillo", Bocadillo.class).list();
        }
    }
}
