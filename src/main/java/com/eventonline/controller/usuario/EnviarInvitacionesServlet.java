package com.eventonline.controller.usuario;

import com.eventonline.dao.MesasDAO;
import com.eventonline.model.Invitados;
import com.eventonline.model.Usuario;
import com.eventonline.service.EmailService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/enviar-invitaciones")
public class EnviarInvitacionesServlet extends HttpServlet {

    private final MesasDAO mesasDAO = new MesasDAO();
    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(new JSONObject().put("success", false).put("error", "No has iniciado sesion").toString());
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
        String nombreEvento = valorOPorDefecto(req.getParameter("nombreEvento"), "Nombre del Evento");
        String fechaEvento = valorOPorDefecto(req.getParameter("fechaEvento"), "09 de Julio, 2026");
        String lugarEvento = valorOPorDefecto(req.getParameter("lugarEvento"), "Jardín de Eventos");

        try {
            List<Invitados> pendientes = mesasDAO.obtenerInvitadosPendientes(usuario.getIdUsuario());

            if (pendientes.isEmpty()) {
                resp.getWriter().write(new JSONObject()
                        .put("success", true)
                        .put("enviados", 0)
                        .put("fallidos", 0)
                        .put("mensaje", "No hay invitados pendientes por notificar.")
                        .toString());
                return;
            }
            int enviados = 0;
            JSONArray fallidos = new JSONArray();

            for (Invitados invitados : pendientes) {
                String nombreMesa = mesasDAO.obtenerNombreMesaDeInvitado(invitados.getIdMesa());
                boolean ok = emailService.enviarInvitacion(invitados, nombreMesa, nombreEvento, fechaEvento, lugarEvento);

                if (ok) {
                    mesasDAO.marcarInvitacionEnviada(invitados.getIdInvitado());
                    enviados++;

                } else {
                    fallidos.put(new JSONObject()
                            .put("correo", invitados.getCorreo())
                            .put("motivo", "No se pudo enviar el correo."));
                }
            }

            JSONObject resultado = new JSONObject()
                    .put("success", true)
                    .put("enviados", enviados)
                    .put("fallidos", fallidos.length())
                    .put("detalleFallidos", fallidos)
                    .put("modoSimulado", !emailService.isConfigurado());

            resp.getWriter().write(resultado.toString());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new JSONObject().put("success", false)
                    .put("error", "Error de base de datos " + e.getMessage()).toString());
        }
    }

    private String valorOPorDefecto(String valor, String porDefecto){
        return (valor == null || valor.trim().isEmpty()) ? porDefecto : valor.trim();
    }
}

