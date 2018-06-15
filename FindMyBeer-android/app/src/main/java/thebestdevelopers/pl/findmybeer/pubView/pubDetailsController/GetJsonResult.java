package thebestdevelopers.pl.findmybeer.pubView.pubDetailsController;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String placeName, vicinity, phone, rating, website;
    private String url;
    public TextView mName, mRating, mAddress, mPhone, mWebsite, tConveniences, tTables;
    public ProgressBar spinner;
    public Button bFav, bMenu;
    private String wifi, adaptedforthedisabled, boardgames, discountsforgroups;
    private String discountsforstudents, roastingroom, toilet;
    private String chair1, chair2, chair4, chair6, chair8;



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
            mRating.setText(rating+"/5");

            tConveniences = activity.findViewById(R.id.tConveniences);
            tTables = activity.findViewById(R.id.tTables);

            spinner = activity.findViewById(R.id.mProgressBarHome);

                String convs = "";

                if (wifi.equals("true"))
                    convs += "WI-FI\n";
                if (adaptedforthedisabled.equals("true"))
                    convs += "Adapted for the disabled\n";
                if (boardgames.equals("true"))
                    convs += "Board games\n";
                //if (discountsforgroups.equals("true"))
                //    convs += "Discounts for groups\n";
                if (discountsforstudents.equals("true"))
                    convs += "Discounts for students\n";
                if (roastingroom.equals("true"))
                    convs += "Roasting room\n";
                if (toilet.equals("true"))
                    convs += "Toilet\n";

                if (convs.equals(""))
                    tConveniences.setText("There are no conveniences");
                else
                    tConveniences.setText(convs);

                String chairs = "1 person table: " + chair1 + "\n" + "2 person table: " + chair2 + "\n" +
                        "4 person table: " + chair4 + "\n" + "6 person table: " + chair6 + "\n" +
                        "8 person table: " + chair8;

                tTables.setText(chairs);
        }
        spinner.setVisibility(View.GONE);
        ScrollView v = activity.findViewById(R.id.bView);
        v.setVisibility(View.VISIBLE);
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
            placeName = googlePlace.get("place_name");
            vicinity = googlePlace.get("vicinity");
            rating = googlePlace.get("rating");

                wifi = googlePlace.get("WI-FI");
                adaptedforthedisabled = googlePlace.get("adapted for the disabled");
                boardgames = googlePlace.get("board games");
                discountsforgroups = googlePlace.get("discounts for groups");
                discountsforstudents = googlePlace.get("discounts for students");
                roastingroom = googlePlace.get("roasting room");
                toilet = googlePlace.get("toilet");

                chair1 = googlePlace.get("chair1");
                chair2 = googlePlace.get("chair2");
                chair4 = googlePlace.get("chair4");
                chair6 = googlePlace.get("chair6");
                chair8 = googlePlace.get("chair8");

    }
}
