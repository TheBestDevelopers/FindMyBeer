package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditTables;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String url;
    public ProgressBar spinner;
    private String chair1, chair2, chair4, chair6, chair8;
    private NumberPicker np1, np2, np4, np6, np8;

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

            np1 = activity.findViewById(R.id.numberPicker1);
            np1.setValue(Integer.parseInt(chair1));

            np2 = activity.findViewById(R.id.numberPicker2);
            np2.setValue(Integer.parseInt(chair2));

            np4 = activity.findViewById(R.id.numberPicker4);
            np4.setValue(Integer.parseInt(chair4));

            np6 = activity.findViewById(R.id.numberPicker6);
            np6.setValue(Integer.parseInt(chair6));

            np8 = activity.findViewById(R.id.numberPicker8);
            np8.setValue(Integer.parseInt(chair8));
            spinner = activity.findViewById(R.id.mProgressBarHome);
        }
        spinner.setVisibility(View.GONE);
        ScrollView v = (ScrollView) activity.findViewById(R.id.bView);
        v.setVisibility(View.VISIBLE);
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
                chair1 = googlePlace.get("chair1");
                chair2 = googlePlace.get("chair2");
                chair4 = googlePlace.get("chair4");
                chair6 = googlePlace.get("chair6");
                chair8 = googlePlace.get("chair8");

    }
}
