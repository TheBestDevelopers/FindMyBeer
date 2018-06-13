package thebestdevelopers.pl.findmybeer.favController;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataFavParser {
    private HashMap<String, String> getFav(JSONObject googlePlaceJson)
    {
        HashMap<String, String> menuList = new HashMap<>();
        String name = "", vicinity="", placeID="";

        Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());

        //TO_DO
        try {
            if (!googlePlaceJson.isNull("name")) {
                name = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }

            if (!googlePlaceJson.isNull("place_id")) {
                placeID = googlePlaceJson.getString("place_id");
            }

            menuList.put("name", name);
            menuList.put("vicinity", vicinity);
            menuList.put("place_id", placeID);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return menuList;

    }

    private List<HashMap<String, String>> getFavs(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placelist = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i = 0; i<count;i++)
        {
            try {
                placeMap = getFav((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        String temp = "{\"results\":";
        String nowy = temp + jsonData + "}";
        jsonData = nowy;

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        JSONObject jObjectResult = null;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getFavs(jsonArray);
    }
}
