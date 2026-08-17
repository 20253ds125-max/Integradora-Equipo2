package com.eventonline.controller.usuario;

import com.eventonline.dao.MesasDAO;
import com.eventonline.model.Invitados;
import com.eventonline.model.Usuario;
import com.eventonline.service.EmailService;
import com.eventonline.util.CorreoElectronico;
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
    private final CorreoElectronico correoElectronico = new CorreoElectronico();

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

        int idReserva;
        try {
            idReserva = Integer.parseInt(req.getParameter("idReserva"));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(new JSONObject().put("success", false).put("error", "idReserva invalido").toString());
            return;
        }

        try {
            MesasDAO.InfoReserva info = mesasDAO.obtenerInfoReserva(idReserva);
            if (info == null || info.idUsuario != usuario.getIdUsuario()) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write(new JSONObject().put("success", false).put("error", "Esta reserva no te pertenece").toString());
                return;
            }

            String nombreEvento = info.nombreSalon != null ? info.nombreSalon : "tu evento";
            String fechaEvento = info.fechaEvento != null ? info.fechaEvento : "";
            String lugarEvento = info.ubicacion != null ? info.ubicacion : nombreEvento;

            List<Invitados> pendientes = mesasDAO.obtenerInvitadosPendientes(idReserva);

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
                boolean ok = correoElectronico.enviarInvitacion(
                        invitados.getCorreo(), invitados.getNombre(), nombreEvento, nombreMesa, fechaEvento, lugarEvento);

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
                    .put("detalleFallidos", fallidos);

            resp.getWriter().write(resultado.toString());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(new JSONObject().put("success", false)
                    .put("error", "Error de base de datos " + e.getMessage()).toString());
        }
    }
}
