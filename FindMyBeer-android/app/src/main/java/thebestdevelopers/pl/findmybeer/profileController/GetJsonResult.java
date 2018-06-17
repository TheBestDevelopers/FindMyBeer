package thebestdevelopers.pl.findmybeer.profileController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    private String url, userName;
    WeakReference<Activity> mWeakActivity;
    Context context;

    public GetJsonResult(Activity activity, Context _context) {
        mWeakActivity = new WeakReference<Activity>(activity);
        context = _context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadProfileUrl downloadUrl = new DownloadProfileUrl(context);
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            return "Exception";
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("placedata", "called parse method");
        Activity activity = mWeakActivity.get();
        if (!s.equals("Exception")) {
            if (activity != null) {
                HashMap<String, String> nearbyPlaceList;
                DataProfileParser parser = new DataProfileParser();
                nearbyPlaceList = parser.parse(s);
                showNearbyPlaces(nearbyPlaceList);
                TextView mName = activity.findViewById(R.id.tLogin);
                mName.setText(userName);
            }
            TextView txt = activity.findViewById(R.id.tLogin);
            txt.setVisibility(View.VISIBLE);
            Button btn = activity.findViewById(R.id.bLogOut);
            btn.setVisibility(View.VISIBLE);
            Button btn2 = activity.findViewById(R.id.bChange);
            btn2.setVisibility(View.VISIBLE);
            Button btn3 = activity.findViewById(R.id.bDelete);
            btn3.setVisibility(View.VISIBLE);
            Button btn4 = activity.findViewById(R.id.bTemp);
            btn4.setVisibility(View.VISIBLE);
            ProgressBar spinner = activity.findViewById(R.id.mProgressBarHome);
            spinner.setVisibility(View.GONE);
            TextView txt2 = activity.findViewById(R.id.tError);
            txt2.setVisibility(View.GONE);
        }
        else {
            TextView mName = activity.findViewById(R.id.tLogin);
            mName.setText("No data");
            mName.setVisibility(View.VISIBLE);
            TextView txt2 = activity.findViewById(R.id.tError);
            txt2.setVisibility(View.VISIBLE);
            ProgressBar spinner = activity.findViewById(R.id.mProgressBarHome);
            spinner.setVisibility(View.GONE);
        }
    }

    private void showNearbyPlaces(HashMap<String, String> googlePlace)
    {
        userName = googlePlace.get("user_name");
    }
}
