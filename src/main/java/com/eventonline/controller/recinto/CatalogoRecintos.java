package com.eventonline.controller.recinto;

import com.eventonline.model.SalonEventos;
import com.eventonline.model.Usuario;
import com.eventonline.service.RecintoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "catalogoServlet", value = "/catalogo")
public class CatalogoRecintos extends HttpServlet {

}