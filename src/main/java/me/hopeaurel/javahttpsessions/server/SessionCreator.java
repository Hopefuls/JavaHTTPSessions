package me.hopeaurel.javahttpsessions.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.hopeaurel.javahttpsessions.sessioning.SessionHandler;
import me.hopeaurel.javahttpsessions.utils.StreamParser;
import me.hopeaurel.javahttpsessions.utils.URIQueryParser;

import java.io.IOException;

public class SessionCreator implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        StreamParser streamParser = new StreamParser(httpExchange.getResponseBody(), httpExchange.getRequestBody());
        URIQueryParser uriQueryParser = new URIQueryParser(httpExchange.getRequestURI().getQuery());

        if (uriQueryParser.getQueryMap().get("clientname").length() < 1) {
            httpExchange.getResponseHeaders().add("Location", "http://localhost:3000");
            httpExchange.sendResponseHeaders(302, "yes".length());
            streamParser.setOutput("yes");
            return;
        }
        SessionHandler sessionHandler = new SessionHandler();

        // TODO FILTER OUT XSS OR HTML FROM THIS!!!
        String clientname = uriQueryParser.getQueryMap().get("clientname").replaceAll("(<.*?>)|(&.*?;)|([ ]{2,})", "");;
        // TODO FILTER OUT XSS OR HTML FROM THIS!!!

        sessionHandler.assignSession(httpExchange.getResponseHeaders(), clientname);
        httpExchange.getResponseHeaders().add("Location", "http://localhost:3000");
        httpExchange.sendResponseHeaders(302, "yes".length());
        streamParser.setOutput("yes");
    }
}
