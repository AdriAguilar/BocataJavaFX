package com.example.bocatajavafx.services;

import com.example.bocatajavafx.dao.UsuarioDAO;
import com.example.bocatajavafx.models.Usuario;
import com.example.bocatajavafx.util.ValidatorUtil;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static String username;

    public LoginResponse loginUsuario(String username, String pw) {
        Usuario usuario = getUsuario(username);
        if (usuario == null) {
            return new LoginResponse(false, "Usuario no encontrado.");
        }
        username = usuario.getUsername();
        if (ValidatorUtil.verifyPassword(pw, usuario.getContrasena())) {
            return new LoginResponse(true, username, usuario.getRol());
        } else {
            return new LoginResponse(false, "¡Contraseña incorrecta!");
        }
    }

    public Usuario getUsuario(String username) {
        return usuarioDAO.getUsuario(username);
    }
}
