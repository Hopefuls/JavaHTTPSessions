package me.hopeaurel.javahttpsessions.sessioning;

import com.sun.net.httpserver.Headers;
import me.hopeaurel.javahttpsessions.utils.CookieParser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionHandler {

    private static Map<String /*SessionID*/, String /*SessionData*/> sessionStorage = new HashMap<>();
    private final String sessionID;

    // Session constructor
    // TODO add more constructor params if needed
    public SessionHandler(String sessionID) {
        this.sessionID = sessionID;
    }

    public SessionHandler() {
        // TODO something later on with this empty constructor
        this.sessionID = null;
    }

    public final boolean isValidession() {
        return sessionStorage.containsKey(this.sessionID);
    }

    public final boolean isValidSession(Headers headers) {
        CookieParser parser = new CookieParser(headers);
        if (parser.getCookie("SESSID").equalsIgnoreCase("no-value")) {
            return false;
        } else {
            String session = parser.getCookie("SESSID");
            return sessionStorage.containsKey(session);
        }
    }

    public final String getSessionData(String sessionID) {
        return sessionStorage.getOrDefault(sessionID, "no data stored");
    }

    // set-cookie/max-age stuff https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie
    // Note: re-read to implement better
    public final String assignSession(Headers headers) {
        try {
            String sessID = generateSessionID();
            headers.add("Set-Cookie", "SESSID="+sessID);
            sessionStorage.put(sessID, "none");
            return sessID;
        } catch (Exception e) {
            System.out.println("Failed to set cookie under assignSESSION");
            return null;
        }
    }

    public final String assignSession(Headers headers, String data) {
        try {
            String sessID = generateSessionID();
            headers.add("Set-Cookie", "SESSID="+sessID);
            sessionStorage.put(sessID, data);
            return sessID;
        } catch (Exception e) {
            System.out.println("Failed to set cookie under assignSESSION");
            return null;
        }
    }

    public final void purge(String sessID) {
        sessionStorage.remove(sessID);
    }

    public final String getCurrentSession(Headers headers) {
    return new CookieParser(headers).getCookie("SESSID");
    }

    public final Map<String, String> getSessionStorage() {
        return sessionStorage;
    }










    private String generateSessionID() {
        String sessID = UUID.randomUUID().toString();
        if (sessionStorage.containsKey(sessID)) {
            return generateSessionID();
        } else {
            return sessID;
        }
    }


}
