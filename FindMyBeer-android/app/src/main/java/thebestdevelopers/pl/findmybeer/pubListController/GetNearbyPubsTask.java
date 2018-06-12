package thebestdevelopers.pl.findmybeer.pubListController;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypeChooser;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GetNearbyPubsTask extends AsyncTask<Object, String, String> {

    public NearbyPubsAsyncResponse delegate = null;

    private String apiData ="";
    private String url;

    public ArrayList<Pub> pubs;

    Boolean timeout = false;

    public GetNearbyPubsTask(NearbyPubsAsyncResponse _delegate) {
        delegate = _delegate;
    }


    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadNearbyPubsUrl downloadUrl = new DownloadNearbyPubsUrl();
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
        delegate.processFinish(s, timeout);
    }


}
