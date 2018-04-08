package thebestdevelopers.pl.findmybeer.pubList;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.pubInfo.DataPubParser;
import thebestdevelopers.pl.findmybeer.pubInfo.DownloadPubUrl;
import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class GetPubListElement extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    public String placeName, vicinity, phone, rating, website, id;
    String url;
    public TextView mName, mRating, mAddress, mPhone, mWebsite;
    private ArrayList<Pub> pubs;

    WeakReference<Activity> mWeakActivity;

    public GetPubListElement(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadPubUrl downloadUrl = new DownloadPubUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        HashMap<String, String> nearbyPlaceList;
        DataPubParser parser = new DataPubParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("placedata","called parse method");
        showNearbyPlaces(nearbyPlaceList);
        Activity activity = mWeakActivity.get();
        if (activity != null) {
            pubs.add(new Pub(placeName,200.0, 3, Double.parseDouble((rating))));

        }
    }

    public void setPubSArray(ArrayList<Pub> _pubs) {
        pubs = _pubs;
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
        placeName = googlePlace.get("place_name");
        vicinity = googlePlace.get("vicinity");
        rating = googlePlace.get("rating");
        website = googlePlace.get("web");
        phone = googlePlace.get("phone");
        id = googlePlace.get("place_id");
        if (website.equals(""))
            website = "There's no website :(";
    }
}
