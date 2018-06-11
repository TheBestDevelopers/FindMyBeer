package thebestdevelopers.pl.findmybeer.searchController.Conveniences;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.pubListController.DownloadNearbyPubsUrl;
import thebestdevelopers.pl.findmybeer.pubListController.NearbyPubsParser;
import thebestdevelopers.pl.findmybeer.pubListController.Pub;
import thebestdevelopers.pl.findmybeer.pubListController.PubListRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypeChooser;


public class GetConveniencesTask extends AsyncTask<Object, String, String> {

    private String apiData ="";
    private String url;

    public ArrayList<String> conveniences;

    WeakReference<Activity> mWeakActivity;
    ProgressBar spinner;
    Boolean timeout = false;

    public GetConveniencesTask(Activity activity, ProgressBar _spinner) {
        mWeakActivity = new WeakReference<Activity>(activity);
        spinner = _spinner;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadUrlWithGetMethod downloadUrl = new DownloadUrlWithGetMethod();
        try {
            apiData = downloadUrl.readUrl(url);
        } catch (java.net.SocketTimeoutException e) {
            timeout = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiData;
    }

    @Override
    protected void onPostExecute(String s){
       // spinner.setVisibility(View.GONE);
        if (timeout) {
            showAlert("Cannot connect to database. Try again later.");
        }
        else {
            ConveniencesParser parser = new ConveniencesParser();
            conveniences = parser.parse(s);
            if (conveniences != null && conveniences.size() != 0) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {

                }
            } else {
                showAlert("There are no places nearby!");
            }
        }
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(mWeakActivity.get(), message, Toast.LENGTH_LONG).show();
    }

}
