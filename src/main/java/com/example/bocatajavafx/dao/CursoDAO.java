package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Curso;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CursoDAO {

    public List<Curso> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Curso", Curso.class).list();
        }
    }
}
