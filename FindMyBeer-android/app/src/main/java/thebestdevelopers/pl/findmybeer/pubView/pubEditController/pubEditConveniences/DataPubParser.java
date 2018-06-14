package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditConveniences;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataPubParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();

        Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());

        try {
            if (!googlePlaceJson.isNull("conveniences")) {
                if (!googlePlaceJson.getJSONObject("conveniences").isNull("WI-FI"))
                    googlePlaceMap.put("WI-FI", googlePlaceJson.getJSONObject("conveniences").getString("WI-FI"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("FACILITIES_FOR_THE_DISABLED"))
                    googlePlaceMap.put("adapted for the disabled", googlePlaceJson.getJSONObject("conveniences").getString("FACILITIES_FOR_THE_DISABLED"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("BOARD_GAMES"))
                    googlePlaceMap.put("board games", googlePlaceJson.getJSONObject("conveniences").getString("BOARD_GAMES"));

                //if (!googlePlaceJson.getJSONObject("conveniences").isNull("discounts for groups"))
                //    googlePlaceMap.put("discounts for groups", googlePlaceJson.getJSONObject("conveniences").getString("discounts for groups"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("DISCOUNTS_FOR_STUDENTS"))
                    googlePlaceMap.put("discounts for students", googlePlaceJson.getJSONObject("conveniences").getString("DISCOUNTS_FOR_STUDENTS"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("ROASTING_ROOM"))
                    googlePlaceMap.put("roasting room", googlePlaceJson.getJSONObject("conveniences").getString("ROASTING_ROOM"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("TOILET"))
                    googlePlaceMap.put("toilet", googlePlaceJson.getJSONObject("conveniences").getString("TOILET"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;

    }

    public HashMap<String, String> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        JSONObject jObjectResult = null;

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
