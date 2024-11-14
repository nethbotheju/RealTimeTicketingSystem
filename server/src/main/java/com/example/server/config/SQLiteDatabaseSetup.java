package com.example.server.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class SQLiteDatabaseSetup {
    // Private property for the database connection
    private static Connection conn;


    private static final ReentrantLock lock = new ReentrantLock();

    // Method to establish the connection
    public static void connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    // Method to create tables
    public static void createTables() {
        String sales = "CREATE TABLE IF NOT EXISTS sales (" +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "numberOfTickets INTEGER NOT NULL" +
                ");";

        String vendors = "CREATE TABLE IF NOT EXISTS vendors (" +
                "id INTEGER PRIMARY KEY," +
                "numOfReleasedTickets INTEGER NOT NULL" +
                ");";

        String customers = "CREATE TABLE IF NOT EXISTS customers (" +
                "id INTEGER PRIMARY KEY," +
                "priority INTEGER NOT NULL," +
                "numOfBoughtTickets INTEGER NOT NULL" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sales);
            stmt.execute(vendors);
            stmt.execute(customers);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    // Method to execute any SQL statement passed as a string with locking mechanism
    public static void executeSQL(String sql) {
        lock.lock();  // Acquire the lock
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("SQL statement executed successfully: " + sql);
        } catch (SQLException e) {
            System.out.println("Execution failed: " + e.getMessage());
        } finally {
            lock.unlock();  // Release the lock
        }
    }


    // Method to close the connection
    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}
