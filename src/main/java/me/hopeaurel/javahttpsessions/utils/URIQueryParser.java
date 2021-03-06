package me.hopeaurel.javahttpsessions.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class URIQueryParser {

    private final String query;


    public URIQueryParser(String query) {
    this.query = query;
    }

    public final Map<String, String> getQueryMap() {
        Map<String, String> result = new HashMap<>();
        Arrays.stream(this.query.split("&")).forEach(s -> {
            String[] split2 = s.split("=");
            result.put(split2[0], split2[1]);
        });
        return result;
    }
}
