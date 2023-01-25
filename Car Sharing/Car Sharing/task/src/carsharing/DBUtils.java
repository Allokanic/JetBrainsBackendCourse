package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    public static Connection getConnection(String url, String pass, String code) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, pass, code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
