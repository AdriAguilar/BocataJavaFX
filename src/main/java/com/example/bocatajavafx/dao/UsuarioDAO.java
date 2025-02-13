package com.example.bocatajavafx.dao;

import com.example.bocatajavafx.models.Usuario;
import com.example.bocatajavafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public Usuario getUsuario(String username) {
        Usuario usuario = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE username = :username", Usuario.class);
            query.setParameter("username", username);

            usuario = query.uniqueResult();
        }
        return usuario;
    }
}
