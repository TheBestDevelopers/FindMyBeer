package thebestdevelopers.pl.findmybeer.profileController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    private String url;
    WeakReference<Activity> mWeakActivity;

    public GetJsonResult(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadProfileUrl downloadUrl = new DownloadProfileUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        Log.d("placedata","called parse method");
        Activity activity = mWeakActivity.get();
        if (activity != null) {
            if (s.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Cannot connect to the server!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        TextView txt = (TextView) activity.findViewById(R.id.tLogin);
        txt.setVisibility(View.VISIBLE);
        Button btn = (Button) activity.findViewById(R.id.bLogOut);
        btn.setVisibility(View.VISIBLE);
        Button btn2 = (Button) activity.findViewById(R.id.bChange);
        btn2.setVisibility(View.VISIBLE);
        Button btn3 = (Button) activity.findViewById(R.id.bDelete);
        btn3.setVisibility(View.VISIBLE);
        Button btn4 = (Button) activity.findViewById(R.id.bTemp);
        btn4.setVisibility(View.VISIBLE);
        ProgressBar spinner = (ProgressBar)activity.findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.GONE);
    }
}
