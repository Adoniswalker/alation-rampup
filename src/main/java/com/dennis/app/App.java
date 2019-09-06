package com.dennis.app;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
//import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
//import net.minidev.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
            HttpContext context = server.createContext("/employees");
            context.setHandler(App::handleEmployees);
            server.start();
        }catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void handleEmployees(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        String protocol = exchange.getRequestMethod();
        OutputStream os = exchange.getResponseBody();
        Headers requestHeaders = exchange.getRequestHeaders();
        switch (protocol){
            case "GET":
                String sql = "SELECT id, first, last, age FROM Employees;";
                DataBaseDetails db = new DataBaseDetails();
                Collection<Map<String, String>> employees = db.queryData(sql);
                db.closeConnection();
                String employeesString = employees.toString();
                final byte[] employeeData = employeesString.getBytes();
                exchange.getResponseHeaders().set("Content-Type", "appication/json");
                exchange.sendResponseHeaders(200, employeeData.length);
                os.write(employeeData);
                break;
            case "POST":
                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
                InputStream is = exchange.getRequestBody();
                byte[] data = new byte[contentLength];
                int length= is.read(data);
                exchange.getResponseHeaders().set("Content-Type", "appication/json");
                exchange.sendResponseHeaders(200, length);
                os.write(data);
                break;
            default:
                String response3 = "Method "+protocol+" not implemented";
                exchange.sendResponseHeaders(200, response3.getBytes().length);
                os.write(response3.getBytes());
        }
        os.close();
    }
}