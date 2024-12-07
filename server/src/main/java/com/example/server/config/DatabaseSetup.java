package com.example.server.config;


import com.example.server.controller.SalesController;
import com.example.server.model.Sale;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseSetup {
    private static Connection connection;

    public static void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            String salesTable = "CREATE TABLE IF NOT EXISTS sales ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "date TEXT NOT NULL,"
                    + "count INTEGER NOT NULL)";

            connection.createStatement().execute(salesTable);

            System.out.println("Database connection established. Including Table creations.");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void insertIntoSales (String date, int count){
        String sql = "INSERT INTO sales (date, count) VALUES (?, ?)";

        try{
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, date);
            psmt.setInt(2, count);
            psmt.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void fetchSales(){
        String sql = "SELECT * FROM sales";

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);){

            ArrayList<Sale> sales = new ArrayList<>();

            while (rs.next()){
                SalesController.sendToFrontendSale(new Sale(rs.getString("date"), rs.getInt("count")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public static void deleteAllSales (){
        String sql = "DELETE FROM sales";

        try{
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
