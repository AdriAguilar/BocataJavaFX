package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.AlumnoDAO;
import com.example.bocatajavafx.dao.CursoDAO;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Curso;
import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.util.ValidatorUtil;

import java.sql.Date;
import java.util.List;

public class AlumnoService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private static String username;

    public void save(Alumno alumno) {
        alumnoDAO.save(alumno);
    }

    public Alumno getById(int id) {
        return alumnoDAO.getById(id);
    }

    public void desactivar(int nia, Date fecha, String motivo) {
        alumnoDAO.desactivar(nia, fecha, motivo);
    }

    public void activar(int nia) {
        alumnoDAO.activar(nia);
    }

    public LoginResponse loginAlumno(String email, String pw) {
        Alumno alumno = getAlumno(email);
        if (alumno == null) {
            return new LoginResponse(false, "Usuario no encontrado.");
        }
        if (alumno.isActivo()) {
            username = alumno.getNombre();
            if (ValidatorUtil.verifyPassword(pw, alumno.getContrasena())) {
                return new LoginResponse(true, username);
            } else {
                return new LoginResponse(false, "¡Credenciales incorrectos!");
            }
        } else {
            return new LoginResponse(false, "Usuario inhabilitado.");
        }
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }

    public Alumno getAlumno(String email) {
        return alumnoDAO.getAlumno(email);
    }

    public Alumno getAlumnoByName(String name) {
        return alumnoDAO.getAlumnoByName(name);
    }

    public List<Pedido> getAlumnoPedidos(int nia) {
        Alumno alumno = alumnoDAO.getAlumnoPedidos(nia);
        return alumno.getPedidos();
    }

    public List<Curso> getCursos() {
        return cursoDAO.getAll();
    }

    public static String getUsername() {
        return username;
    }
}
