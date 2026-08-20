
package com.eventonline.util;

import com.eventonline.model.NotificacionDuenoDTO;
import com.eventonline.model.NotificacionProveedorDTO;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class CorreoElectronico {


    private final String REMITENTE;
    private final String PASSWORD;

    public CorreoElectronico(){
        Dotenv dotenv =Dotenv.configure().ignoreIfMissing().load();
        this.REMITENTE= dotenv.get("CORREO");
        this.PASSWORD= dotenv.get("PASS_CORREO");
    }

    public boolean enviarCorreoHTML(String correoDestino, String asunto, String cuerpoHTML) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE, "Event Online"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject(asunto);
            message.setContent(cuerpoHTML, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (Exception e) {
            System.err.println(" Error al enviar correo a " + correoDestino + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean enviarCodigoVerificacion(String correoDestino, String codigo) {
        String asunto = "Código de Verificación - Event Online";
        String html = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; text-align: center;'>"
                + "  <h2>Verificación de Seguridad</h2>"
                + "  <p>Ingresa el siguiente código de 6 dígitos para continuar:</p>"
                + "  <h1 style='color: #7c5315; letter-spacing: 5px; background: #f8f9fa; padding: 10px; border-radius: 5px;'>" + codigo + "</h1>"
                + "</div></body></html>";

        return enviarCorreoHTML(correoDestino, asunto, html);
    }

    public boolean enviarBienvenida(String correoDestino, String nombreUsuario) {
        String asunto = "¡Bienvenido a Event Online!";
        String html = "<html><body style='font-family: Arial, sans-serif; padding: 20px;'>"
                + "  <h2>¡Hola, " + nombreUsuario + "! 🎉</h2>"
                + "  <p>Gracias por registrarte en <strong>Event Online</strong>. Estamos felices de tenerte con nosotros.</p>"
                + "  <a href='http://localhost:8080/tu-app/login' style='background: #7c5315; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Iniciar Sesión</a>"
                + "</body></html>";

        return enviarCorreoHTML(correoDestino, asunto, html);
    }
    public boolean enviarAceptacionSolicitud(String correoDestino, String nombreRecinto, String urlImagen) {
        String imagenSrc = (urlImagen != null && !urlImagen.trim().isEmpty())
                ? urlImagen.trim()
                : "https://via.placeholder.com/500x250?text=Sin+Foto";

        String asunto = "¡Tu recinto ha sido aprobado!  - Event Online";
        String html = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; text-align: center;'>"

                + "  <img src='" + imagenSrc + "' alt='Portada de " + nombreRecinto + "' style='width: 100%; max-height: 250px; object-fit: cover; border-radius: 8px; margin-bottom: 20px;' />"

                + "  <h2 style='color: #7c5315;'>¡Felicidades!</h2>"
                + "  <p>Nos complace informarte que tu solicitud para publicar <strong>" + nombreRecinto + "</strong> ha sido <strong>aprobada</strong> por nuestro equipo de administración.</p>"
                + "  <p>Tu recinto ya se encuentra activo y visible para todos los clientes en nuestra plataforma.</p>"
                + "  <br><br>"
                + "  <br><br>"
                + "  <p style='font-size: 12px; color: #777;'>Gracias por confiar en Event Online.</p>"
                + "</div></body></html>";

        return enviarCorreoHTML(correoDestino, asunto, html);
    }
    public boolean enviarNotificacionReservaProveedor(NotificacionProveedorDTO dto) {
        String imagenSrc = (dto.getFotoRecinto() != null && !dto.getFotoRecinto().trim().isEmpty())
                ? dto.getFotoRecinto().trim()
                : "https://via.placeholder.com/500x250?text=Sin+Foto";

        String telefono = (dto.getTelefonoCliente() != null) ? dto.getTelefonoCliente() : "No registrado";


        String fechaEvento = (dto.getFecha() != null) ? dto.getFecha() : "Por confirmar";

        String asunto = "¡Has sido contratado para un evento! - Event Online";
        String html = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; text-align: center;'>"

                + "  <img src='" + imagenSrc + "' alt='Foto del lugar' style='width: 100%; max-height: 250px; object-fit: cover; border-radius: 8px; margin-bottom: 20px;' />"

                + "  <h2 style='color: #7c5315;'>¡Nuevo servicio contratado!</h2>"
                + "  <p>El cliente <strong>" + dto.getNombreCliente() + "</strong> ha contratado tus servicios para su evento.</p>"

                + "  <div style='background-color: #f9f9f9; padding: 15px; border-radius: 8px; margin-top: 20px; text-align: left;'>"
                + "    <h3 style='color: #333; margin-top: 0; font-size: 16px;'>Detalles del Evento:</h3>"

                + "    <p style='margin: 5px 0;'><strong>Fecha:</strong> " + fechaEvento + "</p>"
                + "    <p style='margin: 5px 0;'><strong>Recinto:</strong> " + dto.getNombreLugar() + "</p>"
                + "    <p style='margin: 5px 0;'><strong>Ubicación:</strong> " + dto.getUbicacion() + "</p>"
                + "  </div>"

                + "  <div style='background-color: #f9f9f9; padding: 15px; border-radius: 8px; margin-top: 15px; text-align: left;'>"
                + "    <h3 style='color: #333; margin-top: 0; font-size: 16px;'>Contacto del cliente:</h3>"
                + "    <p style='margin: 5px 0;'><strong>Correo:</strong> " + dto.getCorreoCliente() + "</p>"
                + "    <p style='margin: 5px 0;'><strong>Teléfono:</strong> " + telefono + "</p>"
                + "  </div>"

                + "  <br><br>"
                + "  <p style='font-size: 12px; color: #777;'>Gracias por confiar en Event Online.</p>"
                + "</div></body></html>";

        return enviarCorreoHTML(dto.getCorreoDestino(), asunto, html);
    }
    public boolean enviarNotificacionReservaDueno(NotificacionDuenoDTO dto) {
        String imagenSrc = (dto.getFotoRecinto() != null && !dto.getFotoRecinto().trim().isEmpty())
                ? dto.getFotoRecinto().trim()
                : "https://via.placeholder.com/500x250?text=Sin+Foto";

        String telefono = (dto.getTelefonoCliente() != null) ? dto.getTelefonoCliente() : "No registrado";

        String asunto = "¡Nueva Reservación Confirmada! - Event Online";
        String html = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; text-align: center;'>"

                + "  <img src='" + imagenSrc + "' alt='Portada del recinto' style='width: 100%; max-height: 250px; object-fit: cover; border-radius: 8px; margin-bottom: 20px;' />"

                + "  <h2 style='color: #7c5315;'>¡Tienes una nueva reservación!</h2>"
                + "  <p>El cliente <strong>" + dto.getNombreCliente() + "</strong> ha reservado tu espacio.</p>"
                + "  <p><strong>Fecha del evento:</strong> " + dto.getFechaEvento() + "</p>"

                + "  <div style='background-color: #f9f9f9; padding: 15px; border-radius: 8px; margin-top: 20px; text-align: left;'>"
                + "    <h3 style='color: #333; margin-top: 0; font-size: 16px;'>Datos de contacto del cliente:</h3>"
                + "    <p style='margin: 5px 0;'><strong>Correo:</strong> " + dto.getCorreoCliente() + "</p>"
                + "    <p style='margin: 5px 0;'><strong>Teléfono:</strong> " + telefono + "</p>"
                + "  </div>"

                + "  <br><br>"
                + "  <p style='font-size: 12px; color: #777;'>Gracias por confiar en Event Online.</p>"
                + "</div></body></html>";

        return enviarCorreoHTML(dto.getCorreoDestino(), asunto, html);
    }
    public boolean enviarInvitacion(String correoDestino, String nombreInvitado, String nombreEvento,
                                    String nombreMesa, String fechaEvento, String lugarEvento) {

        String asunto = "Tu invitación digital a " + nombreEvento + " - Event Online";
        String html = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 500px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; text-align: center;'>"

                + "  <span style='display:inline-block; background:#f8f9fa; color:#7c5315; font-size:12px; letter-spacing:1px; text-transform:uppercase; padding:6px 14px; border-radius:999px; margin-bottom:14px;'>Invitación Digital</span>"
                + "  <h2 style='color: #7c5315; margin: 6px 0 4px;'>" + nombreEvento + "</h2>"
                + "  <p style='margin:0 0 20px; color:#777;'>Event Online Showcase</p>"

                + "  <p>Hola <strong>" + nombreInvitado + "</strong>, has sido invitado(a) a este evento. Estos son los detalles:</p>"

                + "  <table style='width:100%; border-collapse:collapse; margin:20px 0; text-align:left;'>"
                + "    <tr><td style='padding:6px 0; color:#777; font-size:12px; text-transform:uppercase;'>Fecha</td></tr>"
                + "    <tr><td style='padding:0 0 10px; font-weight:600;'>" + fechaEvento + "</td></tr>"
                + "    <tr><td style='padding:6px 0; color:#777; font-size:12px; text-transform:uppercase;'>Lugar</td></tr>"
                + "    <tr><td style='padding:0 0 10px; font-weight:600;'>" + lugarEvento + "</td></tr>"
                + "    <tr><td style='padding:6px 0; color:#777; font-size:12px; text-transform:uppercase;'>Tu mesa asignada</td></tr>"
                + "    <tr><td style='padding:0 0 10px; font-weight:600;'>" + nombreMesa + "</td></tr>"
                + "  </table>"

                + "  <p style='font-size: 12px; color: #777;'>Presenta este correo como tu pase digital el día del evento.</p>"
                + "</div></body></html>";

        return enviarCorreoHTML(correoDestino, asunto, html);
    }
}


