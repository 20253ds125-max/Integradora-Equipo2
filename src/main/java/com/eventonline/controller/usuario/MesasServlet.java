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
import org.cloudinary.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/mesas-api")
public class MesasServlet extends HttpServlet {

    private final MesasDAO mesasDAO = new MesasDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Usuario usuario = usuarioEnSesion(req);
        resp.setContentType("aplication/json; charset=UTF-8");

        if(usuario == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escribir(resp, new JSONObject().put("success", false).put("error, no has iniciado sesion"));
            return;
        }

        try{
            List<Mesas> mesas = mesasDAO.obtenerMesasPorUsuario(usuario.getIdUsuario());
            escribir(resp, new JSONObject().put("success", true).put("mesas", mesasAJson(mesas)));
        }catch (SQLException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error de base de datos:" + e.getMessage()));

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Usuario usuario = usuarioEnSesion(req);
        resp.setContentType("application/json; charset=UTF-8");

        if (usuario == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            escribir(resp, new JSONObject().put("success", false).put("error", "No has iniciado sesion"));
            return;
        }
        String accion = req.getParameter("accion");
        if (accion == null) accion ="";

        try{
            switch (accion){
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
        }catch (IllegalArgumentException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escribir(resp, new JSONObject().put("success", false).put("error", e.getMessage()));

        }catch (SQLException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribir(resp, new JSONObject().put("success", false).put("error", "Error en la base de datos" + e.getMessage()));

        }
    }
    private void crearMesa(HttpServletRequest req, HttpServletResponse resp, Usuario usuario) throws SQLException, IOException{
        String nombre = req.getParameter("nombre");
        Mesas nueva = new Mesas(nombre, 10, usuario.getIdUsuario());
        mesasDAO.crearMesa(nueva);
        escribir(resp, new JSONObject().put("success", true).put("mesa", mesaAJson(nueva)));

    }

}
