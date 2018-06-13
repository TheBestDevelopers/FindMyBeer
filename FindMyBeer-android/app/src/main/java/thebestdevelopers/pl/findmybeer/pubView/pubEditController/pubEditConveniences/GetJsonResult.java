package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditConveniences;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

            spinner = activity.findViewById(R.id.mProgressBarHome);

            CheckBox chbx1 = (CheckBox) activity.findViewById(R.id.checkbox_wifi);
            CheckBox chbx2 = (CheckBox) activity.findViewById(R.id.checkbox_adapted_disabled);
            CheckBox chbx3 = (CheckBox) activity.findViewById(R.id.checkbox_boardgames);
            CheckBox chbx4 = (CheckBox) activity.findViewById(R.id.checkbox_discountsforgroups);
            CheckBox chbx5 = (CheckBox) activity.findViewById(R.id.checkbox_discountsforstudents);
            CheckBox chbx6 = (CheckBox) activity.findViewById(R.id.checkbox_roastingroom);
            CheckBox chbx7 = (CheckBox) activity.findViewById(R.id.checkbox_toilet);

                if (wifi.equals("true"))
                    chbx1.setChecked(true);
                else if (wifi.equals("false")) {
                    chbx1.setChecked(false);
                }

                if (adaptedforthedisabled.equals("true"))
                    chbx2.setChecked(true);
                else if (adaptedforthedisabled.equals("false")) {
                    chbx2.setChecked(false);
                }


                if (boardgames.equals("true"))
                    chbx3.setChecked(true);
                else if (boardgames.equals("true")) {
                    chbx3.setChecked(false);
                }
                //if (discountsforgroups.equals("true"))
                //    convs += "Discounts for groups\n";

                if (discountsforstudents.equals("true"))
                    chbx5.setChecked(true);
                else if (discountsforstudents.equals("true")) {
                    chbx5.setChecked(false);
                }
                if (roastingroom.equals("true"))
                    chbx6.setChecked(true);
                else if (roastingroom.equals("true")) {
                    chbx6.setChecked(false);
                }
                if (toilet.equals("true"))
                    chbx7.setChecked(true);
                else if (toilet.equals("true")) {
                    chbx7.setChecked(false);
                }

        }
        spinner.setVisibility(View.GONE);
        ScrollView v = (ScrollView) activity.findViewById(R.id.bView);
        v.setVisibility(View.VISIBLE);
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
                wifi = googlePlace.get("WI-FI");
                adaptedforthedisabled = googlePlace.get("adapted for the disabled");
                boardgames = googlePlace.get("board games");
                discountsforgroups = googlePlace.get("discounts for groups");
                discountsforstudents = googlePlace.get("discounts for students");
                roastingroom = googlePlace.get("roasting room");
                toilet = googlePlace.get("toilet");
    }
}
