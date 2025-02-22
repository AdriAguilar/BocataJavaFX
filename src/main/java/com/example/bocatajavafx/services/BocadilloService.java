package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.BocadilloDAO;
import com.example.bocatajavafx.models.Bocadillo;

import java.util.List;

public class BocadilloService {
    private final BocadilloDAO bocadilloDAO = new BocadilloDAO();

    public List<Bocadillo> getAll() {
        return bocadilloDAO.getAll();
    }

    public Bocadillo getById(int id) {
        return bocadilloDAO.getById(id);
    }

    public void save(Bocadillo bocadillo) {
        bocadilloDAO.save(bocadillo);
    }

    public void delete(int id) {
        bocadilloDAO.delete(id);
    }

    public List<Bocadillo> getTodayBocadillos(char dia, int menu) {
        return bocadilloDAO.getAll().stream()
                .filter(bocadillo -> bocadillo.getDia() == dia)
                .filter(bocadillo -> bocadillo.getMenu() == menu)
                .toList();
    }
}
