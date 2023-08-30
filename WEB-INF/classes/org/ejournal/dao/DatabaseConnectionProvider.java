package org.ejournal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    public Connection createDatabaseConnection() throws SQLException {
        Connection connection = connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejournal2.0", "root", "root");
        return connection;
    }
}
