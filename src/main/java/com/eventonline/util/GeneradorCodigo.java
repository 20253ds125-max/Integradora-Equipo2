package com.eventonline.util;

import java.security.SecureRandom;

public class GeneradorCodigo {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int LONGITUD_CODIGO= 6;

    public static String generarCodigo() {
        StringBuilder sb = new StringBuilder( LONGITUD_CODIGO);
        for (int i = 0; i <  LONGITUD_CODIGO; i++) {
            int indice = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(indice));
        }
        return sb.toString();
    }

}
