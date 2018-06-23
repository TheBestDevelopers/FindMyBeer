package thebestdevelopers.pl.findmybeer.menuController;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataMenuParser {

    private HashMap<String, String> getMenu(JSONObject googlePlaceJson) {
        HashMap<String, String> menuList = new HashMap<>();
        String menu = "", size = "";

        Log.d("DataParser", "jsonobject =" + googlePlaceJson.toString());

        try {
            if (!googlePlaceJson.isNull("menu")) {
                menu = googlePlaceJson.getString("menu");
            }
            if (!googlePlaceJson.isNull("size")) {
                size = googlePlaceJson.getString("size");
            }

            menuList.put("menu", menu);
            menuList.put("size", size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public HashMap<String, String> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        JSONObject jObjectResult = null;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getMenu(jsonObject);
    }
}
