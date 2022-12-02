package json;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonUtilities {

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public static JSONArray readJsonFromFile(String path) throws IOException {
        File file = new File(path);
        try (InputStream is = new DataInputStream(new FileInputStream(file))) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    private static String readAll(BufferedReader buffer) throws IOException {
        String line = buffer.readLine();
        String jsonText = "";
        jsonText += line;
        jsonText += "\n";
        while ((line != null) && (!line.isEmpty())) {

            line = buffer.readLine();
            jsonText += line;
            jsonText += "\n";
        }
        return jsonText;
    }
}
