package com.eventonline.service;

import java.time.YearMonth;

public class PagoService {

    public String obtenerErrorTarjeta(String titular, String tarjeta, String vencimiento, String cvv) {
        if (titular == null || titular.trim().isEmpty()) {
            return "El nombre del titular no puede estar vacío.";
        }

        String tarjetaLimpia = (tarjeta != null) ? tarjeta.replaceAll("\\s+", "") : "";
        String vencimientoLimpio = (vencimiento != null) ? vencimiento.replaceAll("\\s+", "") : "";
        String cvvLimpio = (cvv != null) ? cvv.replaceAll("\\s+", "") : "";

        if (!tarjetaLimpia.matches("^[0-9]{16}$")) {
            return "El número de tarjeta debe tener exactamente 16 dígitos.";
        }

        if (!vencimientoLimpio.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
            return "El formato de fecha de vencimiento debe ser MM/YY.";
        }

        try {
            String[] partes = vencimientoLimpio.split("/");
            int mesUsuario = Integer.parseInt(partes[0]);
            int anoUsuario = 2000 + Integer.parseInt(partes[1]);

            YearMonth fechaActual = YearMonth.now();
            YearMonth fechaTarjeta = YearMonth.of(anoUsuario, mesUsuario);

            if (fechaTarjeta.isBefore(fechaActual)) {
                return "La tarjeta ingresada se encuentra vencida.";
            }
        } catch (Exception e) {
            return "La fecha de vencimiento es inválida.";
        }

        if (!cvvLimpio.matches("^[0-9]{3,4}$")) {
            return "El CVV debe contener 3 o 4 dígitos numéricos.";
        }

        return null;
    }

    public boolean validarTarjeta(String titular, String tarjeta, String vencimiento, String cvv) {
        return obtenerErrorTarjeta(titular, tarjeta, vencimiento, cvv) == null;
    }
}