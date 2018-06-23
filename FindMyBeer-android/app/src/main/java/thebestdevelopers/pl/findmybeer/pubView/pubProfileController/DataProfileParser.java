package thebestdevelopers.pl.findmybeer.pubView.pubProfileController;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataProfileParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String userName = "";

        Log.d("DataParser", "jsonobject =" + googlePlaceJson.toString());


        try {
            if (!googlePlaceJson.isNull("username")) {
                userName = googlePlaceJson.getString("username");
            }
            googlePlaceMap.put("user_name", userName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;

    }

    public HashMap<String, String> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        JSONObject jObjectResult = null;
        String temp = "{\"result\":";
        String nowy = temp + jsonData + "}";
        jsonData = nowy;
        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jObjectResult = (JSONObject) jsonObject.get("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlace(jObjectResult);
    }
}
