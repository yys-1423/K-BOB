package com.SPISE.k_bob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OcrResultParser {

    public static List<String> extractInferText(String jsonString) {
        List<String> inferTextList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray imagesArray = jsonObject.getJSONArray("images");

            for (int i = 0; i < imagesArray.length(); i++) {
                JSONObject imageObject = imagesArray.getJSONObject(i);
                JSONArray fieldsArray = imageObject.getJSONArray("fields");

                for (int j = 0; j < fieldsArray.length(); j++) {
                    JSONObject fieldObject = fieldsArray.getJSONObject(j);
                    String inferText = fieldObject.getString("inferText");
                    inferTextList.add(inferText);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return inferTextList;
    }
}
