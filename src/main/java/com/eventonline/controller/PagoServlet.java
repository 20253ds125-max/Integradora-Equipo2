package com.eventonline.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PagoServlet")
public class PagoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String titular = request.getParameter("nombreTitular");
        String tarjeta = request.getParameter("numeroTarjeta");
        String vencimiento = request.getParameter("vencimiento");
        String cvv = request.getParameter("cvv");


        String tarjetaLimpia = (tarjeta != null) ? tarjeta.replace(" ", "") : "";
        String vencimientoLimpio = (vencimiento != null) ? vencimiento.trim() : "";
        String cvvLimpio = (cvv != null) ? cvv.trim() : "";

        System.out.println("=== PROCESANDO PAGO SIMULADO ===");


        boolean formatoFechaValido = vencimientoLimpio.matches("^(0[1-9]|1[0-2])/[0-9]{2}$");
        boolean cvvValido = cvvLimpio.matches("^[0-9]{3,4}$");
        boolean fechaNoVencida = false;


        if (formatoFechaValido) {

            String[] partes = vencimientoLimpio.split("/");
            int mesUsuario = Integer.parseInt(partes[0]);
            int anoUsuario = Integer.parseInt("20" + partes[1]); // Lo convertimos en año completo (2025)


            java.time.YearMonth fechaActual = java.time.YearMonth.now();
            java.time.YearMonth fechaTarjeta = java.time.YearMonth.of(anoUsuario, mesUsuario);


            if (!fechaTarjeta.isBefore(fechaActual)) {
                fechaNoVencida = true;
            }
        }


        if (titular != null && !titular.trim().isEmpty() &&
                tarjetaLimpia.length() >= 16 &&
                formatoFechaValido &&
                fechaNoVencida &&
                cvvValido) {

            request.getSession().setAttribute("pagoExitoso", true);
            System.out.println("-> ¡Pago Aprobado Simulado!");
            response.sendRedirect(request.getContextPath() + "/ticket.html");

        } else {
            System.out.println("-> ¡Pago Rechazado! Datos inválidos o tarjeta ya vencida.");
            request.getSession().setAttribute("pagoExitoso", false);
            response.sendRedirect(request.getContextPath() + "/pago.jsp?error=datos_invalidos");
        }
    }
}