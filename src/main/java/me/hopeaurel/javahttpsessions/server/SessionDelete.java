package me.hopeaurel.javahttpsessions.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.hopeaurel.javahttpsessions.sessioning.SessionHandler;
import me.hopeaurel.javahttpsessions.utils.StreamParser;
import me.hopeaurel.javahttpsessions.utils.URIQueryParser;

import java.io.IOException;

public class SessionDelete implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        StreamParser streamParser = new StreamParser(httpExchange.getResponseBody(), httpExchange.getRequestBody());

        SessionHandler sessionHandler = new SessionHandler();

        if (!sessionHandler.getCurrentSession(httpExchange.getRequestHeaders()).equalsIgnoreCase("no-value")) {
            sessionHandler.purge(sessionHandler.getCurrentSession(httpExchange.getRequestHeaders()));
        }
        httpExchange.getResponseHeaders().add("Location", "http://localhost:3000");
        httpExchange.sendResponseHeaders(302, "yes".length());
        streamParser.setOutput("yes");
    }
}
