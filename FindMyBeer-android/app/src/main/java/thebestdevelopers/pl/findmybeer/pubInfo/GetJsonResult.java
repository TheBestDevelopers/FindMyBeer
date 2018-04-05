package thebestdevelopers.pl.findmybeer.pubInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;

class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    public String placeName, vicinity, phone, rating, website;
    String url;
    public TextView mName, mRating, mAddress, mPhone, mWebsite;

    WeakReference<Activity> mWeakActivity;

    public GetJsonResult(Activity activity) {
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
            mRating.setText("Rating: "+rating+"/5");

            mWebsite = activity.findViewById(R.id.tWebsite);
            mWebsite.setText(website);

            mPhone = activity.findViewById(R.id.tPhone);
            mPhone.setText(phone);
        }
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
            placeName = googlePlace.get("place_name");
            vicinity = googlePlace.get("vicinity");
            rating = googlePlace.get("rating");
            website = googlePlace.get("web");
            phone = googlePlace.get("phone");
            if (website.equals(""))
                website = "There's no website :(";
    }
}
