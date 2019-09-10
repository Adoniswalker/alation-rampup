package com.dennis.app;

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RpcHandler {
    public static void RpcMethodHandler(HttpExchange exchange) throws IOException {
        Helpers.HttpLogger(exchange);
        JsonRpc jsonrpc = new JsonRpc();
        JsonRpcServer rpcServer = new JsonRpcServer();
        OutputStream os = exchange.getResponseBody();
        InputStream is = exchange.getRequestBody();
        String protocol = exchange.getRequestMethod();
        if (!protocol.equals("POST")) {
            String response3 = "Method " + protocol + " not implemented";
            exchange.sendResponseHeaders(200, response3.getBytes().length);
            os.write(response3.getBytes());
        }
        String response;
        try {
            JSONObject json_request_body = Helpers.convert(is);
            response = rpcServer.handle(json_request_body.toString(), jsonrpc);
        } catch (JSONException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            response = "{\"Error\": \"Not a valid Json\", \"message\":\""+e.getMessage()+"\"}";
        }
        final byte[] responseByte = response.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "appication/json");
        exchange.sendResponseHeaders(200, responseByte.length);
        os.write(responseByte);
        os.close();
    }
}