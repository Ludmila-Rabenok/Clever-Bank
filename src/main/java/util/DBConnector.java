package util;

import exception.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is the class for connecting to the database.
 * The class contains a reference to an instance of the Connection interface.
 */
public class DBConnector {
    private static Connection connection;

    private DBConnector() {
        connect();
    }

    /**
     * This is a secure method of obtaining a database connection.
     * @throws  RepositoryException when connecting to a database.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            return connection;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Database connection method.
     *
     * @throws  RepositoryException when connecting to a database.
     */
    private static void connect() {
        try {
            Class.forName(YamlReader.get("driver"));
            connection = DriverManager.getConnection(
                    YamlReader.get("url"),
                    YamlReader.get("user"),
                    YamlReader.get("password"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        }
    }
}
