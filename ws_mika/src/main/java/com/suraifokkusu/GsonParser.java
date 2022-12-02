package com.suraifokkusu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.suraifokkusu.model.Replacement;
import org.json.simple.JSONArray;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonParser {

    public List<Replacement> parse_replacement() {
        try (FileReader reader = new FileReader("replacement.json")) {
            Type itemsListType = new TypeToken<List<Replacement>>() {}.getType();

            List<Replacement> replacementList = new Gson().fromJson(reader, itemsListType);
            return replacementList;

        } catch (Exception e) {
            System.out.println("Parsing error" + e.toString());
        }
        return null;
    }
    public ArrayList<String> parse_data() {
            try (FileReader reader = new FileReader("data.json")) {

                ArrayList <String> dataList  = new Gson().fromJson(reader,ArrayList.class);
                return dataList;

            } catch (Exception e) {
                System.out.println("Parsing error" + e.toString());
            }
            return null;
    }
    public JSONArray parse_resultToJson(ArrayList<String> resultList) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("result.json")) {

            gson.toJson(resultList, writer);

        } catch (Exception e) {
            System.out.println("Parsing error" + e.toString());
        }
        return null;
    }
}
