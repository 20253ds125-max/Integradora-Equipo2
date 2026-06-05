package integradora.dao;
import java.sql.*;

public class Conexion {
    public Connection obtenerConexion()throws SQLException{
        Connection con = null;

        String host=System.getenv("DB_HOST");
        String port=System.getenv("DB_PORT");
        String user= System.getenv("DB_USER");
        String pass= System.getenv("DB_PASSWORD");
        String name= System.getenv("DB_NAME");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=true&requireSSL=true";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}
