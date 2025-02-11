package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.AlumnoDAO;
import com.example.bocatajavafx.models.Alumno;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AlumnoService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();

    public void save(Alumno alumno) {
        // Validar antes de guardar
        alumnoDAO.save(alumno);
    }

    public String loginAlumno(String email, String pw) {
        Alumno alumno = getAlumno(email);
        if (alumno != null && verifyPassword(pw, alumno.getContrasena())) {
            return "Sesión iniciada";
        }
        return "Alumno no logeado";
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }

    public Alumno getAlumno(String email) {
        return alumnoDAO.getAlumno(email);
    }

    public boolean verifyPassword(String pw, String hash) {
        if (hash.startsWith("$2y$")) {
            hash = "$2a$" + hash.substring(4);
        }
        return BCrypt.checkpw(pw, hash);
    }
}
