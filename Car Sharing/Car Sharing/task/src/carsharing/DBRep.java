package carsharing;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class DBRep {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String pass = "";
    private static final String code = "";

    private static String DB_URL;
    private static String dbName;


    public static void initRepo(String _dbName) {
        DB_URL = "jdbc:h2:./src/carsharing/db/" + Objects.requireNonNullElse(_dbName, "name");
        try {
            Connection conn = DBUtils.getConnection(DB_URL, pass, code);
            conn.setAutoCommit(true);

            Statement stmt = conn.createStatement();

            
            stmt.executeUpdate(SQLQueries.initTable1SQL);
            //kostil
            stmt.executeUpdate("ALTER TABLE company ALTER COLUMN id RESTART WITH 1");
            //kostil
            stmt.executeUpdate(SQLQueries.initTable2SQL);

        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static void addCompany(String companyName) {
        try {
            Connection connection = DBUtils.getConnection(DB_URL, pass, code);
            String sqlQuery = "INSERT INTO company (NAME) VALUES (?);";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, companyName);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCar(String name, int company_id) {
        try {
            Connection connection = DBUtils.getConnection(DB_URL, pass, code);
            String sqlQuery = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setInt(2, company_id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllDataFromCompany() {
        try {
            Connection connection = DBUtils.getConnection(DB_URL, pass, code);
            String sqlQuery = "SELECT * FROM company";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);

            return stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getAllDataFromCars(int company_id) {
        try {
            Connection connection = DBUtils.getConnection(DB_URL, pass, code);
            String sqlQuery = "SELECT name FROM car WHERE company_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, company_id);

            return stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCompanyById(int id) {
        try {
            Connection connection = DBUtils.getConnection(DB_URL, pass, code);
            String sqlQuery = "select NAME from COMPANY where ID = ?;";
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getString("name");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
