package com.eventonline.controller.recinto;

import com.eventonline.dao.ReservacionDao;
import com.eventonline.dao.SalonesDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet("/verificarFecha")
public class ComprobarFecha extends HttpServlet {

    private final ReservacionDao reservacionDao = new ReservacionDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fecha = req.getParameter("fecha");
        String idRecinto = req.getParameter("idRecinto");

        boolean disponible = false;
        try {
            if(fecha != null && !fecha.trim().isEmpty() && idRecinto!=null && !idRecinto.trim().isEmpty()){
                int id = Integer.parseInt(idRecinto);

                disponible = reservacionDao.existeReservaRecinto(id,fecha);

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }catch (SQLException e){
           e.printStackTrace();
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print("{\"disponible\": " + disponible + "}");
            out.flush();
        }

    }
}
