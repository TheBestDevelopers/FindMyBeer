package thebestdevelopers.pl.findmybeer.searchController;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubInfo.DataPubParser;
import thebestdevelopers.pl.findmybeer.pubInfo.DownloadPubUrl;


public class GetPubsInfo extends AsyncTask<Object, String, String>{


    private String googlePlacesData;
    public String placeName, vicinity, rating;
    String url;
    public TextView mName, mRating, mAddress;

    WeakReference<Activity> mWeakActivity;

    public GetPubsInfo(Activity activity) {
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

            mName = activity.findViewById(R.id.tName);
            mName.setText(placeName);

            mAddress = activity.findViewById(R.id.tAddress);
            mAddress.setText(vicinity);

            mRating = activity.findViewById(R.id.tRating);
            mRating.setText(rating);

        }
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
        placeName = googlePlace.get("place_name");
        vicinity = googlePlace.get("vicinity");
        rating = googlePlace.get("rating");

    }
}