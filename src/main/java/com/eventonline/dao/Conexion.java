package com.eventonline.dao;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Conexion {

    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_NAME");

        USER = dotenv.get("DB_USER");
        PASS = dotenv.get("DB_PASSWORD");

        if (host == null || port == null || dbName == null || USER == null || PASS == null) {
            System.err.println("[Conexion] ADVERTENCIA: faltan variables DB_HOST/DB_PORT/DB_NAME/DB_USER/DB_PASSWORD. " +
                    "Verifica que exista el archivo .env (copia .env.example) y que este en la ruta que dotenv-java espera " +
                    "(por defecto, el directorio de trabajo del proceso; en IntelliJ+Tomcat normalmente hay que indicar " +
                    "la ruta con .directory(\"ruta/del/proyecto\") o definir las variables como Environment Variables " +
                    "en la configuracion de ejecucion).");
        }

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
