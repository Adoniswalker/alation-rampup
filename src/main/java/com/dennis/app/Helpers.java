package com.dennis.app;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.logging.Logger;

public class Helpers {
    public static JSONObject convert(InputStream is) throws IOException, JSONException {
        StringBuilder requestBuffer = new StringBuilder();
        int rByte;
        while ((rByte = is.read()) != -1) {
            requestBuffer.append((char) rByte);
        }
        JSONObject JsonObject = new JSONObject();
        if (requestBuffer.length() > 0) {
            JsonObject = new JSONObject(URLDecoder.decode(requestBuffer.toString(), "UTF-8"));
        }
        return JsonObject;
    }

    public static void HttpLogger(HttpExchange http) {
        Logger logger = Logger.getLogger("http");
        logger.info(String.format("%s %s %s %s",
                http.getRequestMethod(),
                http.getRequestURI().getPath(),
                http.getRemoteAddress(),
                http.getRequestHeaders().getFirst("User-Agent")));
    }
}