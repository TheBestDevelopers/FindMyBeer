package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditTables;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataPubParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();

        Log.d("DataParser", "jsonobject =" + googlePlaceJson.toString());

        try {
            if (!googlePlaceJson.isNull("tables")) {
                if (!googlePlaceJson.getJSONObject("tables").isNull("chair1"))
                    googlePlaceMap.put("chair1", googlePlaceJson.getJSONObject("tables").getString("chair1"));

                if (!googlePlaceJson.getJSONObject("tables").isNull("chair2"))
                    googlePlaceMap.put("chair2", googlePlaceJson.getJSONObject("tables").getString("chair2"));

                //if (!googlePlaceJson.getJSONObject("tables").isNull("chair3"))
                //    googlePlaceMap.put("chair3", googlePlaceJson.getJSONObject("tables").getString("chair3"));

                if (!googlePlaceJson.getJSONObject("tables").isNull("chair4"))
                    googlePlaceMap.put("chair4", googlePlaceJson.getJSONObject("tables").getString("chair4"));

                //if (!googlePlaceJson.getJSONObject("tables").isNull("chair5"))
                //    googlePlaceMap.put("chair5", googlePlaceJson.getJSONObject("tables").getString("chair5"));

                if (!googlePlaceJson.getJSONObject("tables").isNull("chair6"))
                    googlePlaceMap.put("chair6", googlePlaceJson.getJSONObject("tables").getString("chair6"));

                //if (!googlePlaceJson.getJSONObject("tables").isNull("chair7"))
                //    googlePlaceMap.put("chair7", googlePlaceJson.getJSONObject("tables").getString("chair7"));

                if (!googlePlaceJson.getJSONObject("tables").isNull("chair8"))
                    googlePlaceMap.put("chair8", googlePlaceJson.getJSONObject("tables").getString("chair8"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    public HashMap<String, String> parse(String jsonData) {
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
