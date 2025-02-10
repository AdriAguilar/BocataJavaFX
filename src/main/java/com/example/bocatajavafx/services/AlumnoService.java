package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.AlumnoDAO;
import com.example.bocatajavafx.models.Alumno;

import java.util.List;

public class AlumnoService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();

    public void save(Alumno alumno) {
        //TODO: Validar antes de guardar
        alumnoDAO.save(alumno);
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }
}
