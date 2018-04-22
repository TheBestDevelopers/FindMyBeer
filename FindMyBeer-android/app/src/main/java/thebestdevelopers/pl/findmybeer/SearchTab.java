package thebestdevelopers.pl.findmybeer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.pubList.ItemClickListener;
import thebestdevelopers.pl.findmybeer.pubList.MyRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class SearchTab extends AppCompatActivity implements ItemClickListener {

    private final int REQUEST_CODE = 0;
    private RecyclerView recyclerView;
    private MyRecyclerViewerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Pub> pubs;
    LocationManager lm;
    Location location;
    Double longitude;
    Double latitude;
    private android.location.LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        setContentView(R.layout.activity_search_tab);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs3);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_search).setChecked(true);
        initializeRecyclerView();
        // recyclerView.setHasFixedSize(true);
        initializePubs();

        if (googleServicesAvailable()) {
            manageLocation();

        } else {
            Toast.makeText(this, "There's no Google Services installed", Toast.LENGTH_LONG).show();
        }




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
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
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

    public void mButtonFiltersOnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), Filters.class);
        startActivityForResult(myIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String test = (data.getStringExtra("test"));
        }
    }
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot connect to the play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    public void onClick(View view, int position) {
        final Pub currentPub = pubs.get(position);
        Intent i = new Intent(this, PubInfo.class);
        i.putExtra("placeID", currentPub.getPlaceID());
        startActivity(i);
    }

    private void manageLocation() {
        Toast.makeText(this, "google services working", Toast.LENGTH_LONG).show();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                mAdapter.updateLocation(location);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10.0f, mLocationListener);
    }

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    private void initializePubs() {
        pubs = new ArrayList<>();
        pubs.add(new Pub("trzy siostry", 50.2591173, 19.0266095, 5, 5.0, "ChIJc-kYGzbOFkcRC563sBLpD6w"));
        pubs.add(new Pub("dubai food", 50.2698693,19.0261198, 4, 4.5, "ChIJJ3UMCiXOFkcRzO760q6SSo0"));

        mAdapter = new MyRecyclerViewerAdapter(pubs);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

    }

//        @Override
//    public boolean onSearchRequested() {
//        //pauseSomeStuff();
//        return super.onSearchRequested();
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
