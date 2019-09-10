package com.dennis.app;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EmployeeHandler{
    public static  void handleEmployees(HttpExchange exchange) throws IOException {
        Helpers.HttpLogger(exchange);
        String protocol = exchange.getRequestMethod();
        OutputStream os = exchange.getResponseBody();
        switch (protocol){
            case "GET":
                String sql = "SELECT id, first, last, age FROM Employees;";
                DataBaseDetails db = new DataBaseDetails();
                Collection<Map<String, String>> employees = db.queryData(sql);
                db.closeConnection();
                String employeesString = new Gson().toJson(employees);
                final byte[] employeeData = employeesString.getBytes();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, employeeData.length);
                os.write(employeeData);
                os.close();
                break;
            case "POST":
                post(exchange, os);
                break;
            default:
                String response3 = "Method "+protocol+" not implemented";
                exchange.sendResponseHeaders(200, response3.getBytes().length);
                os.write(response3.getBytes());
        }
    }
    static void post(HttpExchange exchange, OutputStream os) throws IOException {
        InputStream is = exchange.getRequestBody();
        JSONObject response = new JSONObject();
        try {
            response = Helpers.convert(is);
        } catch (JSONException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            try {
                response = new JSONObject().put("Error", "Not a valid Json");
                response.put("message", e.getMessage());
            }catch (JSONException ex){
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final byte[] responseByte = response.toString().getBytes();
        exchange.getResponseHeaders().set("Content-Type", "appication/json");
        exchange.sendResponseHeaders(200, responseByte.length);
        os.write(responseByte);
        os.close();
    }
}