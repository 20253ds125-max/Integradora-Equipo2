package com.eventonline.controller.carrito;

import com.eventonline.model.ItemCarrito;
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
import java.util.List;

@WebServlet("/mi-carrito-de-compra")
public class VerCarritoServlet extends HttpServlet {

    private final CarritoService carritoService = new CarritoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UsuarioLog") == null) {
            request.setAttribute("error", "Debes iniciar sesión.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("UsuarioLog");

        try {
            List<ItemCarrito> items = carritoService.obtenerItemsPorUsuario(usuario.getIdUsuario());

            double subtotal = 0;
            int contRecintos = 0;
            int contServicios = 0;

            for (ItemCarrito item : items) {
                subtotal += item.getPrecio();
                if ("RECINTO".equals(item.getTipo())) {
                    contRecintos++;
                } else {
                    contServicios++;
                }
            }

            double cargoServicio = 0.0;
            double deposito = subtotal * 0.30;
            double total = subtotal + deposito + cargoServicio;

            request.setAttribute("itemsCarrito", items);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("deposito", deposito);
            request.setAttribute("cargoServicio", cargoServicio);
            request.setAttribute("total", total);
            request.setAttribute("contRecintos", contRecintos);
            request.setAttribute("contServicios", contServicios);

            request.getRequestDispatcher("/WEB-INF/mi-carrito-de-compra.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el carrito.");
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}