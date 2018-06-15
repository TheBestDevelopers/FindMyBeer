package thebestdevelopers.pl.findmybeer.pubInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String placeName, vicinity, phone, rating, website;
    private String url, favs = "";
    public TextView mName, mRating, mAddress, mPhone, mWebsite, tConveniences, tTables;
    public ProgressBar spinner;
    public Button bFav, bMenu;
    private String wifi, adaptedforthedisabled, boardgames, discountsforgroups;
    private String discountsforstudents, roastingroom, toilet;
    private String chair1, chair2, chair4, chair6, chair8;
    private String favourite = "", ourPub = "false";


    WeakReference<Activity> mWeakActivity;

    public GetJsonResult(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];
        if (objects.length > 1)
            favs = (String) objects[1];
        DownloadPubUrl downloadUrl = new DownloadPubUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            return "Exception";
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        Activity activity = mWeakActivity.get();
        if (activity != null) {
            spinner = activity.findViewById(R.id.mProgressBarHome);
            if (!s.equals("Exception")) {
                if (favs.equals("add")) {
                    if (s.equals("{\"result\":false}")) {
                        Toast.makeText(activity, "Sth went wrong - already added!", Toast.LENGTH_LONG).show();
                        spinner.setVisibility(View.GONE);
                        return;
                    } else if (s.equals("{\"result\":true}")) {
                        Button btn = activity.findViewById(R.id.bAddFav);
                        btn.setText("Remove from favourites");
                        spinner.setVisibility(View.GONE);
                        return;
                    }
                } else if (favs.equals("remove")) {
                    if (s.equals("{\"result\":false}")) {
                        Toast.makeText(activity, "Sth went wrong - already removed!", Toast.LENGTH_LONG).show();
                        spinner.setVisibility(View.GONE);
                        return;
                    } else if (s.equals("{\"result\":true}")) {
                        Button btn = activity.findViewById(R.id.bAddFav);
                        btn.setText("Add to favourites");
                        spinner.setVisibility(View.GONE);
                        return;
                    }
                }
                HashMap<String, String> nearbyPlaceList;
                DataPubParser parser = new DataPubParser();
                nearbyPlaceList = parser.parse(s);
                Log.d("placedata", "called parse method");
                showNearbyPlaces(nearbyPlaceList);


                mName = activity.findViewById(R.id.tName);
                mName.setText(placeName);

                mAddress = activity.findViewById(R.id.tAddress);
                mAddress.setText(vicinity);

                mRating = activity.findViewById(R.id.tRating);
                mRating.setText(rating + "/5");

                bFav = activity.findViewById(R.id.bAddFav);
                bMenu = activity.findViewById(R.id.bMenu);
                mWebsite = activity.findViewById(R.id.tWebsite);
                mPhone = activity.findViewById(R.id.tPhone);
                tConveniences = activity.findViewById(R.id.tConveniences);
                tTables = activity.findViewById(R.id.tTables);


                if (ourPub.equals("false")) {

                    mWebsite.setText(website);
                    mPhone.setText(phone);

                    bFav.setText("Cannot add to favourites");
                    bFav.setClickable(false);

                    bMenu.setText("There is no menu");
                    bMenu.setClickable(false);

                    tConveniences.setText("There is no info");
                    tTables.setText("There is no info");
                }

                if (ourPub.equals("true")) {
                    mWebsite.setText("There is no website");
                    mPhone.setText("There is no phone");

                    if (favourite.equals("true"))
                        bFav.setText("Remove from favourites");

                    if (favourite.equals("false"))
                        bFav.setText("Add to favourites");

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
            }
            spinner.setVisibility(View.GONE);
            ScrollView v = activity.findViewById(R.id.bView);
            v.setVisibility(View.VISIBLE);
        } else {
            TextView txt2 = activity.findViewById(R.id.tError);
            txt2.setVisibility(View.VISIBLE);
            ProgressBar spinner = activity.findViewById(R.id.mProgressBarHome);
            spinner.setVisibility(View.GONE);
        }
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace) {
        placeName = googlePlace.get("place_name");
        vicinity = googlePlace.get("vicinity");
        rating = googlePlace.get("rating");
        if (googlePlace.get("ourPub").isEmpty()) {
            website = googlePlace.get("web");
            phone = googlePlace.get("phone");
            if (website.equals(""))
                website = "There's no website";
            if (phone.equals(""))
                phone = "There's no phone";

        }
        if (googlePlace.get("ourPub").equals("true")) {
            ourPub = "true";
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

            favourite = googlePlace.get("favourite");
        }
    }
}
