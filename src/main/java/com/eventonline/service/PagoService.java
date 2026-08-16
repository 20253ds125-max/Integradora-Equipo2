package com.eventonline.service;

import java.time.YearMonth;

public class PagoService {

    public boolean validarTarjeta(String titular, String tarjeta, String vencimiento, String cvv) {
        if (titular == null || titular.trim().isEmpty()) {
            System.out.println("--> FALLÓ: Titular vacío");
            return false;
        }

        String tarjetaLimpia = (tarjeta != null) ? tarjeta.replaceAll("\\s+", "") : "";
        String vencimientoLimpio = (vencimiento != null) ? vencimiento.replaceAll("\\s+", "") : "";
        String cvvLimpio = (cvv != null) ? cvv.replaceAll("\\s+", "") : "";

        boolean tarjeta16Digitos = tarjetaLimpia.matches("^[0-9]{16}$");
        boolean formatoFechaValido = vencimientoLimpio.matches("^(0[1-9]|1[0-2])/[0-9]{2}$");
        boolean cvvValido = cvvLimpio.matches("^[0-9]{3,4}$");
        boolean fechaNoVencida = false;

        if (formatoFechaValido) {
            try {
                String[] partes = vencimientoLimpio.split("/");
                int mesUsuario = Integer.parseInt(partes[0]);
                int anoUsuario = 2000 + Integer.parseInt(partes[1]);

                YearMonth fechaActual = YearMonth.now();
                YearMonth fechaTarjeta = YearMonth.of(anoUsuario, mesUsuario);

                if (!fechaTarjeta.isBefore(fechaActual)) {
                    fechaNoVencida = true;
                } else {
                    System.out.println("--> FALLÓ: Tarjeta vencida (" + fechaTarjeta + " es anterior a " + fechaActual + ")");
                }
            } catch (Exception e) {
                fechaNoVencida = false;
            }
        }

        System.out.println("--- LOG DE VALIDACIÓN ---");
        System.out.println("Tarjeta recibida: '" + tarjetaLimpia + "' -> Válida: " + tarjeta16Digitos);
        System.out.println("Fecha recibida: '" + vencimientoLimpio + "' -> Formato Válido: " + formatoFechaValido + " | No Vencida: " + fechaNoVencida);
        System.out.println("CVV recibido: '" + cvvLimpio + "' -> Válido: " + cvvValido);

        return (tarjeta16Digitos && formatoFechaValido && fechaNoVencida && cvvValido);
    }
}