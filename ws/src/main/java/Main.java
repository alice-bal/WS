import json.JsonUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        JSONArray data = JsonUtilities.readJsonFromUrl("https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json");
        JSONArray replacements = JsonUtilities.readJsonFromFile("src/main/resources/replacements.json");

        Map<String, String> clearedReplacements = new HashMap<>();

        for (int i = 0; i < replacements.length(); ++i) {
            JSONObject object = replacements.getJSONObject(i);
            String key = object.getString("replacement");
            String value = null;
            if (!object.get("source").equals(JSONObject.NULL))
                value = object.getString("source");
            clearedReplacements.put(key, value);
        }

        List<String> answerList = new ArrayList<>();

        for (int i = 0; i < data.length(); ++i) {
            String ans = data.getString(i);
            for (String key : clearedReplacements.keySet()) {
                String value = clearedReplacements.get(key);
                if (ans.contains(key) && value == null) {
                    ans = null;
                    break;
                }
                ans = ans.replaceAll(key, clearedReplacements.get(key));
            }
            if (ans != null) answerList.add(ans);
        }

        JSONArray answerJson = new JSONArray(answerList);
        String answerText = answerJson.toString(2);
        System.out.print(answerText);

        Files.write(Paths.get("src/main/resources/answer.json"), Collections.singleton(answerText));

    }

}
