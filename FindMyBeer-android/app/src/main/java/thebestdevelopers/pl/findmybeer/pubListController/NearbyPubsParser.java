package thebestdevelopers.pl.findmybeer.pubListController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearbyPubsParser {

    private ArrayList<Pub> getPubs(JSONArray pubsListJsonArray)
    {
        ArrayList<Pub> pubs = new ArrayList<>();

        try {
           for (int i =0; i< pubsListJsonArray.length(); ++i) {
               JSONObject singlePubInfo = pubsListJsonArray.getJSONObject(i);
               String pubName = singlePubInfo.getString("name");
               Integer freeTables = singlePubInfo.getInt("freeTable");
               Double rating = singlePubInfo.getDouble("rating");
               StringBuilder idBuilder = new StringBuilder("!!!");
               idBuilder.append(singlePubInfo.getString("id"));
               String id = idBuilder.toString();

               Integer distance = singlePubInfo.getInt("distance");
               Pub pub = new Pub(pubName, distance, freeTables, rating, id);
               pubs.add(pub);
           }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return pubs;

    }

    public ArrayList<Pub> parse(String jsonData)
    {
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPubs(jsonObject);
    }

}
