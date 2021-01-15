package me.hopeaurel.javahttpsessions.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HTMLLoader {


    public static String loadResource(String name) throws IOException {

        ClassLoader classLoader = HTMLLoader.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(name);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


        /*
        ArrayList<String> results = new ArrayList<>();
        File[] files = new File(HTMLLoader.class.getClassLoader().getResource("HTMLData/").toURI()).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        ClassLoader classLoader = HTMLLoader.class.getClassLoader();

        File file = new File(classLoader.getResource("HTMLData/").getFile());

        //File is found
        System.out.println("File Found : " + file.exists());

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);


         */
    }

