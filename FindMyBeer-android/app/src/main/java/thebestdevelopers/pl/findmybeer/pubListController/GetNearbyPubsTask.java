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
import thebestdevelopers.pl.findmybeer.searchController.SortingTypeChooser;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GetNearbyPubsTask extends AsyncTask<Object, String, String> implements ItemClickListener {

    private String apiData ="";
    private String url;

    public ArrayList<Pub> pubs;

    WeakReference<Activity> mWeakActivity;
    private PubListRecyclerViewerAdapter mAdapter;
    Location location;
    SortingTypeChooser  sortingTypeChooser = new SortingTypeChooser();
    ProgressBar spinner;
    Boolean timeout = false;

    public GetNearbyPubsTask(Activity activity, ProgressBar _spinner, Location _location) {
        mWeakActivity = new WeakReference<Activity>(activity);
        location = _location;
        sortingTypeChooser = new SortingTypeChooser();
        spinner = _spinner;
    }

    @Override
    public void onClick(View view, int position) {
        final Pub currentPub = pubs.get(position);
        Intent i = new Intent(mWeakActivity.get(), PubInfo.class);
        i.putExtra("placeID", currentPub.getPlaceID());
        mWeakActivity.get().startActivity(i);
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
        spinner.setVisibility(View.GONE);
        if (timeout) {
            Toast.makeText(mWeakActivity.get(),"Cannot connect to database. Try again later.", Toast.LENGTH_SHORT).show();
        }
        else {
            NearbyPubsParser parser = new NearbyPubsParser();
            pubs = parser.parse(s);
            Log.d("placedata", "called parse method");
            if (pubs != null && pubs.size() != 0) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {
                    setRecyclerView(activity);
                    managePubsAdapter();
                }
            } else {
                Toast.makeText(mWeakActivity.get(), "There are no places nearby!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setRecyclerView(Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.my_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mAdapter = new PubListRecyclerViewerAdapter(pubs);
        recyclerView.setAdapter(mAdapter);
    }

    private void managePubsAdapter() {
        sortingTypeChooser.setListToSort(pubs);
        mAdapter.setClickListener(this);
        pubs = sortingTypeChooser.getSortedList("distance ascending");
        mAdapter.notifyDataSetChanged();
    }

}
