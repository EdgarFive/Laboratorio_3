package umg.progra2.DataBase.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:sqlite:C:\\\\Users\\\\GMG\\\\Desktop\\\\PROGRAMACIÓN (Programas)\\\\0. JAVA-AA\\\\0. PDF Guardados\\\\baselite.db"; // Cambia el nombre del archivo según sea necesario

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

