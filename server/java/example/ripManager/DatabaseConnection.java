package example.ripManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String dbUrl = System.getenv("DB_URL");
    private static final String dbUser = System.getenv("DB_USER");
    private static final String dbPassword = System.getenv("DB_PASSWORD");
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
