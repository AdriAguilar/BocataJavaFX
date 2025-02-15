package com.example.bocatajavafx.services;

import com.example.bocatajavafx.controllers.MainController;
import com.example.bocatajavafx.dao.PedidoDAO;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.models.Pedido;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final List<Character> DIAS = List.of('L', 'M', 'X', 'J', 'V');
    private static final Map<Character, String> NOMBRES_DIAS = Map.of(
            'L', "Lunes",
            'M', "Martes",
            'X', "Miércoles",
            'J', "Jueves",
            'V', "Viernes"
    );

    public void save(Pedido pedido) {
        if (!pedidoDAO.hasOrderedToday(MainController.getAlumno().getNia())) {
            pedidoDAO.save(pedido);
        }
    }

    public List<Pedido> getTodayPedidos() {
        return pedidoDAO.getTodayPedidos();
    }



    public BocadilloResponse createPedido(Alumno alumno, String tipoBocadillo) {
        if (pedidoDAO.hasOrderedToday(alumno.getNia())) {
            return new BocadilloResponse(false, "Ya has realizado un pedido hoy.");
        }

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        Character dia = obtenerDiaLetra(dayOfWeek);

        if (dia == null) return new BocadilloResponse(false, "No se pueden hacer pedidos los fines de semana.");

        Bocadillo bocadillo = pedidoDAO.getBocadilloByDiaYTipo(dia, tipoBocadillo, 1);

        if (bocadillo == null) new BocadilloResponse(false, "No existe el bocadillo seleccionado.");
        System.out.println(bocadillo.getNombre() + "  -  " + bocadillo.getTipo() + "  -  " + bocadillo.getDia());

        Date today = Date.valueOf(LocalDate.now());
        Pedido pedido = new Pedido();
        pedido.setAlumno(alumno);
        pedido.setBocadillo(bocadillo);
        pedido.setFecha(today);
        pedido.setCosteTotal(bocadillo.getCoste());
        pedido.setEstado("Pendiente");

        pedidoDAO.save(pedido);

        return new BocadilloResponse(true);
    }

    private Character obtenerDiaLetra(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return 'L';
            case TUESDAY:
                return 'M';
            case WEDNESDAY:
                return 'X';
            case THURSDAY:
                return 'J';
            case FRIDAY:
                return 'V';
            default:
                return null;
        }
    }
}
