package thebestdevelopers.pl.findmybeer.searchController.Conveniences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ConveniencesParser {

    private ArrayList<String> getConveniences(JSONArray conveniencesLJsonArray)
    {
        ArrayList<String> conveniences = new ArrayList<>();

        try {
            for (int i =0; i< conveniencesLJsonArray.length(); ++i) {
                conveniences.add(conveniencesLJsonArray.get(i).toString());
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return conveniences;

    }

    public ArrayList<String> parse(String jsonData)
    {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getConveniences(jsonArray);
    }
}
