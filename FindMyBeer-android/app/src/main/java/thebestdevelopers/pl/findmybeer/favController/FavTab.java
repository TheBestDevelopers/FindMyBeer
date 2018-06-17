package thebestdevelopers.pl.findmybeer.favController;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;

public class FavTab extends AppCompatActivity {

    String mId;
    ArrayList<PubData> mfavList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_fav_tab);

        TextView txt = findViewById(R.id.tFavs);
        TextView txt2 = findViewById(R.id.tError);
        txt.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.GONE);
        RelativeLayout v = findViewById(R.id.bView);
        v.setVisibility(View.GONE);
        ProgressBar spinner = findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.VISIBLE);

        BottomNavigationView tabs = findViewById(R.id.navigationtabs2);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_fav).setChecked(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RecyclerView recyclerView = findViewById(R.id.fav_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //przekazane info z pubinfo

        String mId = "8";

        String url = getUrl(mId);
        GetJsonResult getFavData = new GetJsonResult(this, getApplicationContext());
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        getFavData.execute(dataTransfer);

        //trzeba przeslac do bazy
        //dodanie elementow do listy - pobranie z bazy
        //tymczasowe rozwiazanie

        //recyclerView.setAdapter(new FavRecyclerViewAdapter(mfavList, recyclerView));

        Intent i;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                i = new Intent(getApplicationContext(), HomeTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_search:
                                i = new Intent(getApplicationContext(), SearchTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_map:
                                i = new Intent(getApplicationContext(), MapTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_user:
                                i = new Intent(getApplicationContext(), ProfileTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                        }
                        return true;
                    }
                });
    }

    //TO_DO
    //http://localhost:8080/api/favourites/getFavourites?userID=9
    private String getUrl(String id) {
        StringBuilder menuUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        menuUrl.append("/api/favourites/getFavourites?userID=");
        menuUrl.append(id);
        Log.d("created url", menuUrl.toString());
        return menuUrl.toString();
    }
}
