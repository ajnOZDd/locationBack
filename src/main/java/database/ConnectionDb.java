package database ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
    private String databaseUrl;
    private String username;
    private String password;
    private Connection connection;
    private String database ;
    public ConnectionDb(String username, String password, String database) {
        this.databaseUrl = "jdbc:postgresql://localhost:5432/"+database; // Default database URL
        this.username = username;
        this.password = password;
    }

    public ConnectionDb(String databaseUrl, String username, String password , String database) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
    }

    public Connection open() {
        try {
            // Establish a connection
            connection = DriverManager.getConnection(this.getDatabaseUrl(), this.getUsername(), this.getPassword());
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public void rollback() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                System.out.println("Transaction rolled back.");
            }
        } catch (SQLException e) {
            System.out.println("Error rolling back transaction: " + e.getMessage());
        }
    }

    public void commit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
                System.out.println("Transaction committed.");
            }
        } catch (SQLException e) {
            System.out.println("Error committing transaction: " + e.getMessage());
        }
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}