package com.suraifokkusu;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.suraifokkusu.model.Replacement;

public class Main {
    public static final String API_URL = "https://raw.githubusercontent.com/thewhitesoft/student-2022-assignment/main/data.json";
    public static void main(String[] args) throws IOException, InterruptedException {
        GsonParser parser = new GsonParser();
        List<Replacement> replacementList = parser.parse_replacement();
/*
----------------------------------------------------------------------------------------
HTTP CLIENT для GET-запроса data.json
----------------------------------------------------------------------------------------
*/
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // parse JSON
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> dataList = mapper.readValue(response.body(), new TypeReference<ArrayList<String>>() {});

        //Если использовать парсер локального data.json
        //ArrayList<String> dataList = parser.parse_data();
/*
----------------------------------------------------------------------------------------
Сначала я прогоняю замены на наличие поля source = null и удаляю их из data
----------------------------------------------------------------------------------------
*/
        for (Replacement replacement : replacementList) {
            if (replacement.getSource() == null) {
                /*
                Дополнительный счетчик для прогонки data.json
                */
                for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++)                 {
                    if (dataList.get(dataIndex).contains(replacement.getReplacement())) {
                        dataList.remove(dataIndex);
                        /*
                        При удалении из списка след элемент встает на место удаленного, потому указатель смещаем на одну позицию влево
                        */
                        if (dataIndex != 0)
                            dataIndex -= 1; 
                    }
                }

            }
        }

/*
----------------------------------------------------------------------------------------
Данный блок делится на две части:
1. Нахождение максимально длинной замены, которая включает в себя data.
2. Нахождение последней встречающейся замены.

После найденная замена вставляется в массив data.
----------------------------------------------------------------------------------------
*/
        int replacementIndex = -1;
        int biggerLengthReplace = 0;
        for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {

            for (int index = 0; index < replacementList.size(); index++) {
                if (dataList.get(dataIndex).contains(replacementList.get(index).getReplacement()) && replacementList.get(index).getReplacement().length() > biggerLengthReplace) { //dataList.get(dataIndex).equals(replacementList.get(index).getReplacement())
                    biggerLengthReplace = replacementList.get(index).getReplacement().length();
                    replacementIndex = index;
                }
            }
            for (int replacementMeetingIndex = 0; replacementMeetingIndex < replacementList.size(); replacementMeetingIndex++) {
                if (replacementList.get(replacementIndex).getReplacement().equals(replacementList.get(replacementMeetingIndex).getReplacement())) {
                    replacementIndex = replacementMeetingIndex;
                }
            }
            dataList.set(dataIndex, (dataList.get(dataIndex).replace(replacementList.get(replacementIndex).getReplacement(), replacementList.get(replacementIndex).getSource())));     //etDataMessage(replacementList.get(index).getSource());
            biggerLengthReplace = 0;

        }
/*
----------------------------------------------------------------------------------------
*/
        parser.parse_resultToJson(dataList);
    }
}






