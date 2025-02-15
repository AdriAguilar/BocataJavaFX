package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.PedidoDAO;
import com.example.bocatajavafx.models.Pedido;

import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public List<Pedido> getTodayPedidos() {
        return pedidoDAO.getTodayPedidos();
    }
}
