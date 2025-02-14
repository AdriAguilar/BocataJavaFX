package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.BocadilloDAO;
import com.example.bocatajavafx.models.Bocadillo;

import java.util.List;

public class BocadilloService {
    private final BocadilloDAO bocadilloDAO = new BocadilloDAO();

    public List<Bocadillo> getAll() {
        return bocadilloDAO.getAll();
    }
}
