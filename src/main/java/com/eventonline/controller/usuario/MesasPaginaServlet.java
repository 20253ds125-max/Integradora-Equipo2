package com.eventonline.controller.usuario;

import com.eventonline.dao.MesasDAO;
import com.eventonline.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/mesas")
public class MesasPaginaServlet extends HttpServlet {

    private final MesasDAO mesasDAO = new MesasDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            req.setAttribute("error", "Primero inicia sesion para gestionar tus mesas");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");

        String idReservaParam = req.getParameter("idReserva");
        int idReserva;
        try {
            idReserva = Integer.parseInt(idReservaParam);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Esa reserva no existe o no te pertenece.");
            req.getRequestDispatcher("/app/perfil").forward(req, resp);
            return;
        }
        try {
            MesasDAO.InfoReserva info = mesasDAO.obtenerInfoReserva(idReserva);

            if (info == null || info.idUsuario != usuario.getIdUsuario()) {
                req.setAttribute("error", "Esa reserva no existe o no te pertenece.");
                req.getRequestDispatcher("/app/perfil").forward(req, resp);
                return;
            }
        if (!"CONFIRMADA".equalsIgnoreCase(info.estado)) {
            req.setAttribute("error", "Solo puedes gestionar mesas de reservas ya confirmadas (pagadas).");
            req.getRequestDispatcher("/app/perfil").forward(req, resp);
            return;
        }
        if (info.capacidadSalon == null) {
            req.setAttribute("error", "Esta reserva no tiene un salon con capacidad definida.");
            req.getRequestDispatcher("/app/perfil").forward(req, resp);
            return;

        }
        req.setAttribute("idReserva", idReserva);
        req.setAttribute("nombreSalon", info.nombreSalon);
        req.setAttribute("ubicacion", info.ubicacion);
        req.setAttribute("fechaEvento", info.fechaEvento);
        req.setAttribute("capacidadSalon", info.capacidadSalon);
        req.setAttribute("maxMesas", info.maxMesas());
        req.getRequestDispatcher("/WEB-INF/mesas.jsp").forward(req, resp);

    } catch(SQLException e){
        throw new ServletException(e);
    }
}
}
