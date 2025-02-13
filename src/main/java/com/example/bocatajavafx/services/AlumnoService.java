package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.AlumnoDAO;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.util.ValidatorUtil;

import java.util.List;

public class AlumnoService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private static String username;

    public void save(Alumno alumno) {
        // Validar antes de guardar
        alumnoDAO.save(alumno);
    }

    public LoginResponse loginAlumno(String email, String pw) {
        Alumno alumno = getAlumno(email);
        if (alumno == null) {
            return new LoginResponse(false, "Usuario no encontrado.");
        }
        username = alumno.getNombre();
        if (ValidatorUtil.verifyPassword(pw, alumno.getContrasena())) {
            return new LoginResponse(true, username);
        } else {
            return new LoginResponse(false, "¡Contraseña incorrecta!");
        }
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }

    public Alumno getAlumno(String email) {
        return alumnoDAO.getAlumno(email);
    }

    public static String getUsername() {
        return username;
    }
}
