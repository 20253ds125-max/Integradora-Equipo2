package com.eventonline.service;

import com.eventonline.model.Invitados;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private final String host;
    private final String port;
    private final String user;
    private final String password;
    private final String fromName;
    private final boolean configurado;

    public EmailService(){
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        this.host = dotenv.get("MAIL_HOST");
        this.port = dotenv.get("MAIL_PORT", "587");
        this.user =dotenv.get("MAIL_USER");
        this.password = dotenv.get("MAIL_PASSWORD");
        this.fromName = dotenv.get("MAIL_FROM_NAME", "Event Online");

        this.configurado = host != null && !host.isBlank()
                && user != null && !user.isBlank()
                && password != null && !password.isBlank();
    }

    public boolean isConfigurado(){
        return configurado;
    }


    public boolean enviarInvitacion(Invitados invitados, String nombreMesa, String nombreEvento,
                                    String fechaEvento, String lugarEvento){
        String asunnto = "Tu invitacion digital" + nombreEvento;
        String cuerpoHtml = construirCuerpoHtml(invitados, nombreMesa, nombreEvento, fechaEvento, lugarEvento);

        if (!configurado){
            System.out.println("[EmailService] SIMULACION de envio " +
                    "Destinatario" + invitados.getCorreo() + " Asunto " +asunnto);
            return true;
        }

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", port);

        Session session = Session.getInstance(propiedades, new Authenticator() {
        @Override
        protected  PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(user, password);
        }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(invitados.getCorreo()));
            message.setSubject(asunnto, "UTF-8");
            message.setContent(cuerpoHtml, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        }catch (Exception e){
            System.out.println("Error enviando a" + invitados.getCorreo() + ":" + e.getMessage());
            return  false;
        }
    }

    private String construirCuerpoHtml(Invitados invitados, String nombreMesa, String nombreEvento,
                                       String fechaEvento, String lugarEvento) {
        return "<div style=\"font-family:Arial,sans-serif;max-width:480px;margin:0 auto;border:1px solid #e2ddd5;border-radius:12px;overflow:hidden\">"
                + "<div style=\"background:#2f2a26;color:#e8c988;padding:18px 24px;\">"
                + "<span style=\"font-size:12px;letter-spacing:.08em;text-transform:uppercase;\">Invitación Digital</span>"
                + "<h2 style=\"margin:6px 0 0;color:#fff;\">" + escapar(nombreEvento) + "</h2>"
                + "</div>"
                + "<div style=\"padding:24px;color:#2f2a26;\">"
                + "<p>Hola <strong>" + escapar(invitados.getNombre()) + "</strong>,</p>"
                + "<p>Has sido invitado(a) a este evento. Estos son los detalles:</p>"
                + "<table style=\"width:100%;border-collapse:collapse;margin:16px 0;\">"
                + fila("Fecha", fechaEvento)
                + fila("Lugar", lugarEvento)
                + fila("Tu mesa asignada", nombreMesa)
                + "</table>"
                + "<p style=\"font-size:13px;color:#7a726a;\">Presenta este correo como tu pase digital el día del evento.</p>"
                + "</div></div>";
    }

    private String fila (String etiqueta, String valor){
        return "<tr><td style=\"padding:6px 0;color:#7a726a;font-size:12px;text-transform:uppercase;\">" + escapar(etiqueta) + "</td></tr>"
                + "<tr><td style=\"padding:0 0 10px;font-weight:600;\">" + escapar(valor) + "</td></tr>";
    }

    private String escapar(String texto){
        if (texto == null)return "";
        return texto.replace("&","&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
