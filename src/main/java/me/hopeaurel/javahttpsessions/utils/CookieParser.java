package me.hopeaurel.javahttpsessions.utils;

import com.sun.net.httpserver.Headers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CookieParser {

    private final Map<String /*key*/, String /*value*/> dataMap = new HashMap<>();

    private final Headers headers;
    private final boolean empty;

    public CookieParser(Headers headers) {
        this.headers = headers;
        if (this.headers.get("Cookie") == null) {
            // Making sure the Cookie header is not empty, as no cookies are no use to us
            this.empty = true;
        } else {
            this.empty = false;

            // Loop through the cookies of the header and add them to the map for later querying
            this.headers.get("Cookie").forEach(s -> Arrays.stream(s.split("; ")).forEach(s1 -> {
                String[] cookies = s1.split("=");
                dataMap.put(cookies[0], cookies[1]);

            }));

        }

    }

    public final String getCookie(String key) {
        // smart me used empty instead of this.empty 4head
        if (this.empty) {
            // just return "no-value" when the cookie is empty, can be handled through the according codepoint
            return "no-value";
        }
        // otherwise return value, return "no-value" when it isn't empty, but has nothing
        return this.dataMap.getOrDefault(key, "no-value");
    }
}
