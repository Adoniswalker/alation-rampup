package com.dennis.app;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
            HttpContext context = server.createContext("/employees");
            context.setHandler(EmployeeHandler::handleEmployees);
            HttpContext rpc = server.createContext("/rpc-server");
            rpc.setHandler(RpcHandler::RpcMethodHandler);
            server.start();
        }catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}