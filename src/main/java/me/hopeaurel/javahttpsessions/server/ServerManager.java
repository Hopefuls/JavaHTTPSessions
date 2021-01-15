package me.hopeaurel.javahttpsessions.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerManager {

    public static HttpServer server;
    public static void __init__() {
        try {
            server = HttpServer.create(new InetSocketAddress(3000), 0);
            server.createContext("/", new HttpContextHandler());
            server.createContext("/createSession", new SessionCreator());
            server.createContext("/deleteSession", new SessionDelete());
            server.setExecutor(null); // creates a default executor
            server.start();

            // logging loggers
            System.out.println("Session example started under "+server.getAddress().getHostName()+":"+server.getAddress().getPort());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
