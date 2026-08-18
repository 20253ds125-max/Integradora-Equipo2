package com.eventonline.dao;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class Conexion {

    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        USER = dotenv.get("DB_USER") != null ? dotenv.get("DB_USER") : System.getenv("DB_USER");
        PASS = dotenv.get("DB_PASSWORD") != null ? dotenv.get("DB_PASSWORD") : System.getenv("DB_PASSWORD");

        // Obtenemos la URL de entorno o usamos la cadena con Wallet explícito
        String envUrl = dotenv.get("DB_URL") != null ? dotenv.get("DB_URL") : System.getenv("DB_URL");

        if (envUrl != null && !envUrl.isEmpty()) {
            URL = envUrl;
        } else {
            // Si es local, usa localhost
            String host = dotenv.get("DB_HOST");
            String port = dotenv.get("DB_PORT");
            String dbName = dotenv.get("DB_NAME");
            URL = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName;
        }

        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}