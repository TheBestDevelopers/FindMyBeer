package thebestdevelopers.pl.findmybeer.mapsController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject json) {
        HashMap<String, String> placesMap = new HashMap<>();
        String pName = "";
        String vicinity = "";
        String latitude = "";
        String longitude = "";
        String reference = "";
        try {
            if (!json.isNull("name")) {
                pName = json.getString("name");
            }
            if (!json.isNull("vicinity")) {
                pName = json.getString("vicinity");
            }
            latitude = json.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = json.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = json.getString("reference");
            placesMap.put("place_name", pName);
            placesMap.put("vicinity", vicinity);
            placesMap.put("lat", latitude);
            placesMap.put("lng", longitude);
            placesMap.put("reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placesMap;
    }

    private List<HashMap<String, String>>getPlaces(JSONArray json) {
        int size = json.length();
        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        for (int i =0; i< size; i++) {
            try {
                placeMap = getPlace((JSONObject) json.get(i));
                list.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<HashMap<String, String>> parse (String data) {
        JSONArray jArray = null;
        JSONObject jObject;
        try {
            jObject = new JSONObject(data);
            jArray = jObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jArray);
    }
}
