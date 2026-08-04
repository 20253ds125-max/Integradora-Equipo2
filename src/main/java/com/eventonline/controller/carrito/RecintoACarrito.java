package com.eventonline.controller.carrito;

import com.eventonline.model.Usuario;
import com.eventonline.service.CarritoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/carritoAgregar")
public class RecintoACarrito extends HttpServlet {
    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("UsuarioLog") == null) {
            req.setAttribute("error","sesion no iniciada");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);

        }

        try {


            int idPublicacionEvento = Integer.parseInt(req.getParameter("idPublicacionEventos"));

            Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");
            int idUsuario= usuario.getIdUsuario();

            boolean agregado = carritoService.agregarRecintoCarrito(idUsuario, idPublicacionEvento);

            if (agregado) {
                req.setAttribute("exito","Recinto Agregado al carrito");
                req.getRequestDispatcher("/catalogo").forward(req,resp);
            } else {
                resp.sendRedirect("error.jsp?msg=NoSePudoAgregar");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error","Error al cargar los recintos: " + e.getMessage());
            req.getRequestDispatcher("/catalogo").forward(req,resp);
        } catch (SQLException e) {
            req.setAttribute("error","Error al cargar los recintos: " + e.getMessage());
            req.getRequestDispatcher("/catalogo").forward(req,resp);

        }
    }
}
