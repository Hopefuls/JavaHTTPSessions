package me.hopeaurel.javahttpsessions.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.hopeaurel.javahttpsessions.sessioning.SessionHandler;
import me.hopeaurel.javahttpsessions.utils.HTMLLoader;
import me.hopeaurel.javahttpsessions.utils.StreamParser;


import java.io.IOException;

public class HttpContextHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        StreamParser streamParser = new StreamParser(httpExchange.getResponseBody(), httpExchange.getRequestBody());
        SessionHandler sessionHandler = new SessionHandler();

        if (!sessionHandler.isValidSession(httpExchange.getRequestHeaders())) {

            String html = HTMLLoader.loadResource("sessioncreate.html");

            httpExchange.getResponseHeaders().add("Content-Type", "text/html");
            assert html != null;
            httpExchange.sendResponseHeaders(200, html.length());
            streamParser.setOutput(html);
        } else {
            String currentSession = sessionHandler.getCurrentSession(httpExchange.getRequestHeaders());
            String sessiondata = sessionHandler.getSessionData(currentSession);
            // TODO filter out html/xss etc (nice practice to do that)
            StringBuilder sb = new StringBuilder();
            sessionHandler.getSessionStorage().forEach((s, s2) -> sb.append("- ").append(s2).append(" | ").append(s+" <br>"));
            String response = HTMLLoader.loadResource("mainpage.html").replace("{SESSIONID}", currentSession).replace("{SESSIONNAME}", sessiondata).replace("{SESSIONLIST}", sb.toString());


            httpExchange.getResponseHeaders().add("Content-Type", "text/html");
            httpExchange.sendResponseHeaders(200, response.length());
            streamParser.setOutput(response);
        }

    }
}
