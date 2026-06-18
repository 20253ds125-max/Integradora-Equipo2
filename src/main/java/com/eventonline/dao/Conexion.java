package com.eventonline.dao;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Conexion {

    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        Dotenv dotenv = Dotenv.configure().load();

        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_NAME");

        USER = dotenv.get("DB_USER");
        PASS = dotenv.get("DB_PASSWORD");

        URL = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection obtenerConexion()throws SQLException{
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
