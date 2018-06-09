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
import android.view.MenuItem;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;

public class FavTab extends AppCompatActivity {

    String mId, mName, mAddress;
    ArrayList<PubData> mfavList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_fav_tab);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs2);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_fav).setChecked(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fav_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //przekazane info z pubinfo
        if (mfavList == null)
            mfavList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            mId = (String) b.get("placeID"); //wczytanie id miejsca oznaczonego markerem
            mName = (String) b.get("placeName");
            mAddress = (String) b.get("placeAddress");
                if (mId != null && mName != null && mAddress != null)
                    mfavList.add(new PubData(mId, mName, mAddress));
        }


        //trzeba przeslac do bazy
        //dodanie elementow do listy - pobranie z bazy
        //tymczasowe rozwiazanie
        for (int temp = 0; temp<10;temp++)
            mfavList.add(new PubData("", "Pub Name", "Pub address"));

        recyclerView.setAdapter(new FavRecyclerViewAdapter(mfavList, recyclerView));

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
                                break;
                            case R.id.action_search:
                                i = new Intent(getApplicationContext(), SearchTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                            case R.id.action_map:
                                i = new Intent(getApplicationContext(), MapTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                            case R.id.action_user:
                                i = new Intent(getApplicationContext(), ProfileTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                        }
                        return true;
                    }
                });
    }
}
