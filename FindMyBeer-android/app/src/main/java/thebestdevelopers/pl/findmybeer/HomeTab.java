package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.List;

import thebestdevelopers.pl.findmybeer.mapsController.MapTab;

public class HomeTab extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerViewerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Pub> pubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometab);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

       // recyclerView.setHasFixedSize(true);

        initializePubs();
        mAdapter = new MyRecyclerViewerAdapter(pubs);
        recyclerView.setAdapter(mAdapter);
       //

        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs1);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_home).setChecked(true);

        Intent i;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
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

    private void initializePubs() {
        pubs = new ArrayList<>();
        pubs.add(new Pub("Klubowa", 200.0, 4, 4.5));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));
        pubs.add(new Pub("Ministerstwo", 100.0, 2, 4.0));

        //mAdapter.notifyDataSetChanged();
    }
}
