package carsharing;

public class SQLQueries {
    public static final String initTable1SQL =
            "CREATE TABLE IF NOT EXISTS company " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL UNIQUE);";
    public static final String initTable2SQL =
            "CREATE TABLE IF NOT EXISTS car (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL UNIQUE, " +
            "company_id INTEGER NOT NULL, " +
            "CONSTRAINT FK_ID FOREIGN KEY (company_id) REFERENCES COMPANY(id));";
    public static final String dropping = "DROP TABLE company";
}
