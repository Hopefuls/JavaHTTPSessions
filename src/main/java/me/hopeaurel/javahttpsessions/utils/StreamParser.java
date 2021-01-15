package me.hopeaurel.javahttpsessions.utils;

import java.io.*;

public class StreamParser {

    private final OutputStream outputStream;
    private final InputStream inputStream;

    public StreamParser(OutputStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }


    public final String getString() {
        String str = "empty";
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(this.inputStream))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            str = response.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public final void setOutput(String str) {
        try {
            this.outputStream.write(str.getBytes());
            this.outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
