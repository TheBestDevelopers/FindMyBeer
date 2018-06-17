package thebestdevelopers.pl.findmybeer.pubView.pubProfileController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

public class GetJsonResult extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    private String url;
    WeakReference<Activity> mWeakActivity;
    private Context context;

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
    }
}
