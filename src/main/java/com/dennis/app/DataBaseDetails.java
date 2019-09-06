package com.dennis.app;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DataBaseDetails {
    //    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_URL = System.getenv("DATABASE_URL");

    static final String USER = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");
    private Connection conn = null;
    private Statement stmt = null;

    public DataBaseDetails() {
        try {
            //jdbc driver
            Class.forName("org.postgresql.Driver");
            //opening connection
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            //execute query
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    Collection<Map<String, String>> queryData(String sql) {
        try {
            ResultSet rs = stmt.executeQuery(sql);
            Collection<Map<String, String>> employees = new HashSet<Map<String, String>>();
            while (rs.next()) {
                Map<String, String> employee = new HashMap<String, String>();
                employee.put("id", Integer.toString(rs.getInt("id")));
                employee.put("age", rs.getString("age"));
                employee.put("first", rs.getString("first"));
                employee.put("last", rs.getString("last"));
                employees.add(employee);
            }
            rs.close();
            return employees;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    void insertData(String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
