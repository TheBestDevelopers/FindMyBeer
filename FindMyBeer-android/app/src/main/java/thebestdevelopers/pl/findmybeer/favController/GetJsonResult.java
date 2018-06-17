package thebestdevelopers.pl.findmybeer.favController;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String url;

    private String name, vicinity, placeID;
    public ArrayList<PubData> mFavList;
    private Context context;

    WeakReference<Activity> mWeakActivity;

    public GetJsonResult(Activity activity, Context _context) {
        mWeakActivity = new WeakReference<Activity>(activity);
        context = _context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadFavUrl downloadUrl = new DownloadFavUrl(context);
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            return "Exception";
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        Activity activity = mWeakActivity.get();
        if (!s.equals("Exception")) {
            List<HashMap<String, String>> menuList;
            DataFavParser parser = new DataFavParser();
            menuList = parser.parse(s);
            Log.d("placedata", "called parse method");
            showMenu(menuList);

            if (activity != null) {
                RecyclerView recyclerView = activity.findViewById(R.id.fav_list);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(new FavRecyclerViewAdapter(mFavList, recyclerView));
            }
            TextView txt = activity.findViewById(R.id.tFavs);
            txt.setVisibility(View.VISIBLE);
            RelativeLayout v = activity.findViewById(R.id.bView);
            v.setVisibility(View.VISIBLE);
            ProgressBar spinner = activity.findViewById(R.id.mProgressBarHome);
            spinner.setVisibility(View.GONE);
        } else {
            TextView txt = activity.findViewById(R.id.tFavs);
            txt.setVisibility(View.VISIBLE);
            TextView txt2 = activity.findViewById(R.id.tError);
            txt2.setVisibility(View.VISIBLE);
            ProgressBar spinner = activity.findViewById(R.id.mProgressBarHome);
            spinner.setVisibility(View.GONE);
        }
    }

    private void showMenu(List<HashMap<String, String>> googlePlace) {
        mFavList = new ArrayList<>();
        for (int i = 0; i < googlePlace.size(); i++) {
            name = googlePlace.get(i).get("name");
            vicinity = googlePlace.get(i).get("vicinity");
            placeID = googlePlace.get(i).get("place_id");
            String temp = "!!!"+placeID;
            placeID = temp;
            mFavList.add(new PubData(name, vicinity,placeID));
        }
    }
}
