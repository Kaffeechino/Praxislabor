package de.gfn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String HOST = "jdbc:mysql://localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "praxislabor";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        // DSN - Data Source Name: "jdbc:mysql://localhost:3306/german_postal_codes"
        return DriverManager.getConnection(HOST + ":" + PORT + "/" + DB_NAME, USER, PASSWORD);
    }
}
