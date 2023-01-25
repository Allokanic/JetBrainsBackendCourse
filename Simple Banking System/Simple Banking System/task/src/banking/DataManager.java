package banking;

import java.sql.*;

import org.sqlite.SQLiteDataSource;

public class DataManager {
    private final String tableName = "card";
    private final String url;


    DataManager(String baseName) {
        String url = "jdbc:sqlite:" + baseName;
        this.url = url;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement test = con.createStatement()) {
                test.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                        "id integer primary key ," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INT default 0);");
            } catch (SQLException e) {
                System.out.println("inner");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("not inner");
            e.printStackTrace();
        }

    }

    public boolean checkAccount(String cardNumber, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire = "SELECT * FROM " + tableName + " WHERE number = ? AND pin = ?;";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setString(1, cardNumber);
                test.setString(2, pin);
                try (ResultSet result = test.executeQuery()) {
                    return result.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAccountCard(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire = "SELECT * FROM " + tableName + " WHERE number = ?;";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setString(1, cardNumber);
                try (ResultSet result = test.executeQuery()) {
                    return result.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addToBase(String cardNumber, String pinCode) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire = "INSERT INTO " + tableName + " (number, pin) VALUES (?, ?);";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setString(1, cardNumber);
                test.setString(2, pinCode);
                test.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(String card, int value) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire =
                    "UPDATE " + tableName + " SET balance = balance + ? WHERE number = ?";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setInt(1, value);
                test.setString(2, card);
                test.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void closeAccount(String card) {
        System.out.println("\nThe account has been closed!");
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire =
                    "DELETE FROM " + tableName + " WHERE number = ?";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setString(1, card);
                test.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getBalance(String card) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            String inquire = "SELECT * FROM " + tableName + " WHERE number = ?;";
            try (PreparedStatement test = con.prepareStatement(inquire)) {
                test.setString(1, card);
                try (ResultSet result = test.executeQuery()) {
                    return result.getInt(4);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void makeTransfer(String from, String to, int value) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            String inquireUp = "UPDATE " + tableName + " SET balance = balance + ? WHERE number = ?";
            String inquireDown = "UPDATE " + tableName + " SET balance = balance - ? WHERE number = ?";
            try (PreparedStatement makeUp = con.prepareStatement(inquireUp);
                 PreparedStatement makeDown = con.prepareStatement(inquireDown)) {

                makeUp.setInt(1,value);
                makeUp.setString(2, to);
                makeUp.executeUpdate();

                makeDown.setInt(1, value);
                makeDown.setString(2, from);
                makeDown.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}