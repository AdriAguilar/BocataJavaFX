package com.example.bocatajavafx.util;

import org.mindrot.jbcrypt.BCrypt;

public class ValidatorUtil {
    public static boolean verifyPassword(String pw, String hash) {
        if (hash.startsWith("$2y$")) {
            hash = "$2a$" + hash.substring(4);
        }
        return BCrypt.checkpw(pw, hash);
    }

    public static String hashPassword(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    public static boolean isAlumno(String text) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return text.matches(regex);
    }
}
