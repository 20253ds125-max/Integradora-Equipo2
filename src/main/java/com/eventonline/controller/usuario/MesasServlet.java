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
import java.util.List;

@WebServlet("/mesas-api")
public class MesasServlet extends HttpServlet {

    private final MesasDAO mesasDAO = new MesasDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario usuario = usuarioEnSesion(req);
        resp.setContentType("aplication/json; charset=UTF-8");

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escribir(resp, new JSONObject().put("success", false).put("error","Error, no has iniciado sesion"));
            return;
        }

        try {
            List<Mesas> mesas = mesasDAO.obtenerMesasPorUsuario(usuario.getIdUsuario());
            escribir(resp, new JSONObject().put("success", true).put("mesas", mesasAJson(mesas)));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error de base de datos:" + e.getMessage()));

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
            switch (accion) {
                case "crearMesa" -> crearMesa(req, resp, usuario);
                case "renombrarMesa" -> renombrarMesa(req, resp, usuario);
                case "eliminarMesa" -> eliminarMesa(req, resp, usuario);
                case "agregarInvitado" -> agregarInvitado(req, resp, usuario);
                case "eliminarInvitado" -> eliminarInvitado(req, resp, usuario);
                default -> {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    escribir(resp, new JSONObject().put("success", false).put("error", "Accion no reconocisa"));

                }
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escribir(resp, new JSONObject().put("success", false).put("error", e.getMessage()));

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error en la base de datos" + e.getMessage()));

        }
    }

    private void crearMesa(HttpServletRequest req, HttpServletResponse resp, Usuario usuario) throws SQLException, IOException {
        String nombre = req.getParameter("nombre");
        Mesas nueva = new Mesas(nombre, 10, usuario.getIdUsuario());
        mesasDAO.crearMesa(nueva);
        escribir(resp, new JSONObject().put("success", true).put("mesa", mesasAJson(nueva)));

    }

    private void renombrarMesa(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        String nombre = req.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mesa es obligatorio");

        }
        boolean actualizado = mesasDAO.renombrarMesa(idMesa, nombre.trim(), usuario.getIdUsuario());
        if (!actualizado) {
            throw new IllegalArgumentException("La mesa no existe o no te pertenece");
        }
        escribir(resp, new JSONObject().put("succes", true));
    }

    private void eliminarMesa(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        boolean eliminado = mesasDAO.eliminarMesa(idMesa, usuario.getIdUsuario());
        if (!eliminado) {
            throw new IllegalArgumentException("La mesa no existe o no te pertenece");

        }
        escribir(resp, new JSONObject().put("success", true));
    }

    private void agregarInvitado(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws SQLException, IOException {
        int idMesa = parseEntero(req.getParameter("idMesa"), "idMesa");
        String nombre = req.getParameter("nombre");
        String correo = req.getParameter("correo");

        Invitados invitados = new Invitados(nombre, correo, idMesa);
        mesasDAO.agregarInvitado(invitados, usuario.getIdUsuario());
        escribir(resp, new JSONObject().put("success", true).put("invitado", invitadosAJson(invitados)));

    }

    private void eliminarInvitado(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws SQLException, IOException {
        int idInvitados = parseEntero(req.getParameter("idInvitados"), "idInvitados");
        boolean eliminado = mesasDAO.eliminarInvitado(idInvitados, usuario.getIdUsuario());
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
            throw new IllegalArgumentException("El campo" + campo + "es invalido");
        }
    }
    private void escribir(HttpServletResponse resp, JSONObject json) throws IOException{
    resp.getWriter().write(json.toString());
    }

    private JSONArray mesasAJson(List<Mesas> mesas){
        JSONArray arreglo = new JSONArray();
        for (Mesas mesa : mesas){
            arreglo.put(mesasAJson(mesas));
        }
        return arreglo;
    }

    private JSONObject mesasAJson(Mesas mesas){
        JSONArray invitado = new JSONArray();
        for (Invitados invitados : mesas.getInvitados()){
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
