package com.dennis.app;

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;
import com.google.gson.internal.$Gson$Preconditions;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONException;

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
            rpc.setHandler(App::RpcHandler);
            server.start();
        }catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void RpcHandler(HttpExchange exchange) {
        JsonRpc jsonrpc = new JsonRpc();
        JsonRpcServer rpcServer = new JsonRpcServer();
        String textRequest = "{\"jsonrpc\": \"2.0\",\"method\": \"add\",\"params\": {\"player\": \"jack\"},\"id\": \"1\"}";
        String response = rpcServer.handle(textRequest, jsonrpc);
        System.out.print(response);
    }
}