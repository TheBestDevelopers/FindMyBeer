package thebestdevelopers.pl.findmybeer.pubInfo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataPubParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "";
        String vicinity = "";
        String phone = "";
        String rating = "";
        String website = "";
        String favourite = "";
        String ourPub = "";

      //  Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());


        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating");
            }
            if (!googlePlaceJson.isNull("website")) {
                website = googlePlaceJson.getString("website");
            }
            if (!googlePlaceJson.isNull("international_phone_number")) {
                phone = googlePlaceJson.getString("international_phone_number");
            }
            if (!googlePlaceJson.isNull("favourite")) {
                favourite = googlePlaceJson.getString("favourite");
            }
            if (!googlePlaceJson.isNull("ourPub")) {
                ourPub = googlePlaceJson.getString("ourPub");
            }

            if (!googlePlaceJson.isNull("conveniences")) {
                if (!googlePlaceJson.getJSONObject("conveniences").isNull("WI-FI"))
                    googlePlaceMap.put("WI-FI", googlePlaceJson.getJSONObject("conveniences").getString("WI-FI"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("adapted for the disabled"))
                    googlePlaceMap.put("adapted for the disabled", googlePlaceJson.getJSONObject("conveniences").getString("adapted for the disabled"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("board games"))
                    googlePlaceMap.put("board games", googlePlaceJson.getJSONObject("conveniences").getString("board games"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("discounts for groups"))
                    googlePlaceMap.put("discounts for groups", googlePlaceJson.getJSONObject("conveniences").getString("discounts for groups"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("discounts for students"))
                    googlePlaceMap.put("discounts for students", googlePlaceJson.getJSONObject("conveniences").getString("discounts for students"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("roasting room"))
                    googlePlaceMap.put("roasting room", googlePlaceJson.getJSONObject("conveniences").getString("roasting room"));

                if (!googlePlaceJson.getJSONObject("conveniences").isNull("toilet"))
                    googlePlaceMap.put("toilet", googlePlaceJson.getJSONObject("conveniences").getString("toilet"));
            }
            if(!googlePlaceJson.isNull("tables")) {
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



            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("rating", rating);
            googlePlaceMap.put("web", website);
            googlePlaceMap.put("phone", phone);
            googlePlaceMap.put("favourite", favourite);
            googlePlaceMap.put("ourPub", ourPub);

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
