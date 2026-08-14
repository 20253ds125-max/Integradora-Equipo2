package com.eventonline.controller.usuario;

import com.eventonline.dao.MesasDAO;
import com.eventonline.model.Invitados;
import com.eventonline.model.Mesas;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;


@WebServlet("/mesas-api")
public class MesasServlet extends HttpServlet {

    private final MesasDAO mesasDAO = new MesasDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuario = usuarioEnSesion(req);
        resp.setContentType("application/json; charset=UTF-8");

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error, no has iniciado sesion"));
            return;
        }

        try {
            int idReserva = parseEntero(req.getParameter("idReserva"), "idReserva");
            MesasDAO.InfoReserva info = validarAccesoReserva(idReserva, usuario, resp);
            if (info == null) return;

            List<Mesas> mesas = mesasDAO.obtenerMesasPorReserva(idReserva);
           int totalInvitados = mesas.stream().mapToInt(m -> m.getInvitados().size()).sum();

           escribir(resp, new JSONObject()
                   .put("success", true)
                   .put("mesas", mesasAJson(mesas))
                   .put("maxMesas", info.capacidadSalon)
                   .put("capacidadSalon", info.capacidadSalon)
                   .put("nombreSalon", info.nombreSalon)
                   .put("totalInvitados", totalInvitados));

        }catch (IllegalArgumentException e ){
          resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          escribir(resp, new JSONObject().put("success", false).put("error", e.getMessage()));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error de base de datos: " + e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuario = usuarioEnSesion(req);
        resp.setContentType("application/json; charset=UTF-8");

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escribir(resp, new JSONObject().put("success", false).put("error", "No has iniciado sesion"));
            return;
        }
        String accion = req.getParameter("accion");
        if (accion == null) accion = "";

        try {
            int idReserva = parseEntero(req.getParameter("idReserva"), "idReseva");
            MesasDAO.InfoReserva info = validarAccesoReserva(idReserva, usuario, resp);
            if (info == null ) return;

            switch (accion) {
                case "crearMesa" -> crearMesa(req, resp, idReserva, info);
                case "renombrarMesa" -> renombrarMesa(req, resp, idReserva);
                case "eliminarMesa" -> eliminarMesa(req, resp, idReserva);
                case "agregarInvitado" -> agregarInvitado(req, resp, idReserva);
                case "eliminarInvitado" -> eliminarInvitado(req, resp, idReserva);
                default -> {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    escribir(resp, new JSONObject().put("success", false).put("error", "Accion no reconocida"));
                }
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escribir(resp, new JSONObject().put("success", false).put("error", e.getMessage()));

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error en la base de datos: " + e.getMessage()));
        }
    }

    private MesasDAO.InfoReserva validarAccesoReserva(int idReserva, Usuario usuario, HttpServletResponse resp)
        throws SQLException, IOException{

        MesasDAO.InfoReserva info = mesasDAO.obtenerInfoReserva(idReserva);

        if (info == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            escribir(resp, new JSONObject().put("success", false).put("error", "la reserva no existe"));
            return null;
        }
        if (info.idUsuario != usuario.getIdUsuario()){
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            escribir(resp, new JSONObject().put("success", false).put("error", "Esta reserva no te pertenece"));
            return null;
        }
        if (!"CONFIRMADA".equalsIgnoreCase(info.estado)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escribir(resp, new JSONObject().put("success", false)
                    .put("error", "Solo puedes gestionar mesas de reservas ya confirmadas (pagadas)"));
            return null;
        }
        if (info.capacidadSalon == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escribir(resp, new JSONObject().put("success", false)
                    .put("error", "Esta reserva no tiene un salon con capacidad definida"));
            return null;
        }
        return info;

    }

    private void crearMesa(HttpServletRequest req, HttpServletResponse resp, int idReserva, MesasDAO.InfoReserva info) throws SQLException, IOException {
        String nombre = req.getParameter("nombre");
        Mesas nueva = new Mesas(nombre, MesasDAO.INVITADOS_POR_MESA, idReserva);
        mesasDAO.crearMesa(nueva, info);
        escribir(resp, new JSONObject().put("success", true).put("mesa", mesasAJson(nueva)));
    }

    private void renombrarMesa(HttpServletRequest req, HttpServletResponse resp, int idReserva)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        String nombre = req.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mesa es obligatorio");
        }
        boolean actualizado = mesasDAO.renombrarMesa(idMesa, nombre.trim(), idReserva);
        if (!actualizado) {
            throw new IllegalArgumentException("La mesa no existe o no te pertenece");
        }
        escribir(resp, new JSONObject().put("success", true));
    }

    private void eliminarMesa(HttpServletRequest req, HttpServletResponse resp, int idReserva)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        boolean eliminado = mesasDAO.eliminarMesa(idMesa, idReserva);
        if (!eliminado) {
            throw new IllegalArgumentException("La mesa no existe o no te pertenece");
        }
        escribir(resp, new JSONObject().put("success", true));
    }

    private void agregarInvitado(HttpServletRequest req, HttpServletResponse resp, int idReserva)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        String nombre = req.getParameter("nombre");
        String correo = req.getParameter("correo");

        Invitados invitados = new Invitados(nombre, correo, idMesa);
        mesasDAO.agregarInvitado(invitados, idReserva);
        escribir(resp, new JSONObject().put("success", true).put("invitado", invitadosAJson(invitados)));
    }

    private void eliminarInvitado(HttpServletRequest req, HttpServletResponse resp, int idReserva)
            throws SQLException, IOException {
        int idInvitados = parseEntero(req.getParameter("idInvitado"), "idInvitado");
        boolean eliminado = mesasDAO.eliminarInvitado(idInvitados, idInvitados);
        if (!eliminado) {
            throw new IllegalArgumentException("El invitado no existe o no te pertenece");
        }
        escribir(resp, new JSONObject().put("success", true));
    }

    private Usuario usuarioEnSesion(HttpServletRequest req) {
        HttpSession sesion = req.getSession(false);
        if (sesion == null) return null;
        return (Usuario) sesion.getAttribute("UsuarioLog");
    }

    private int parseEntero(String valor, String campo) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + campo + " es invalido");
        }
    }

    private void escribir(HttpServletResponse resp, JSONObject json) throws IOException {
        resp.getWriter().write(json.toString());
    }

    private JSONArray mesasAJson(List<Mesas> mesas) {
        JSONArray arreglo = new JSONArray();
        for (Mesas mesa : mesas) {
            arreglo.put(mesasAJson(mesa));
        }
        return arreglo;
    }

    private JSONObject mesasAJson(Mesas mesas) {
        JSONArray invitado = new JSONArray();
        for (Invitados invitados : mesas.getInvitados()) {
            invitado.put(invitadosAJson(invitados));
        }
        return new JSONObject()
                .put("idMesa", mesas.getIdMesa())
                .put("nombre", mesas.getNombre())
                .put("capacidad", mesas.getCapacidad())
                .put("invitados", invitado);
    }

    private JSONObject invitadosAJson(Invitados invitados) {
        return new JSONObject()
                .put("idInvitado", invitados.getIdInvitado())
                .put("nombre", invitados.getNombre())
                .put("correo", invitados.getCorreo())
                .put("idMesa", invitados.getIdMesa())
                .put("invitacionEnviada", invitados.isInvitacionEnviada());
    }
}
