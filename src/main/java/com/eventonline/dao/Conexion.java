package com.eventonline.dao;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Conexion {
    public Connection obtenerConexion()throws SQLException{
        Connection con = null;
        Dotenv dotenv = Dotenv.configure().load();

        String host=dotenv.get("DB_HOST");
        String port=dotenv.get("DB_PORT");
        String user= dotenv.get("DB_USER");
        String pass= dotenv.get("DB_PASSWORD");
        String dbName= dotenv.get("DB_NAME");

        String url = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return con;
    }
}
