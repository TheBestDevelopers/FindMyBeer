package thebestdevelopers.pl.findmybeer.searchController;

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
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlWithGetMethod;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.pubListController.ItemClickListener;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.pubListController.NearbyPubsParser;
import thebestdevelopers.pl.findmybeer.pubListController.PubListRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.pubListController.Pub;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypeChooser;

public class SearchTab
        extends
        AppCompatActivity
        implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        ItemClickListener  {

    private final int REQUEST_CODE = 0;
    private ArrayList<Pub> pubs = new ArrayList<>();
    private String sortingType = "distance ascending";
    private PubListRecyclerViewerAdapter mAdapter;
    SortingTypeChooser  sortingTypeChooser;
    RecyclerView recyclerView;
    ArrayList<String> conveniences;
    private Double longitude = 0.0, latitude = 0.0;
    private ProgressBar spinner;
    Boolean newLocationSet = false;
    ItemClickListener itemClickListener;
    HttpRequests httpRequests;
    Button buttonChooseFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tab);
        overridePendingTransition(0, 0);
        setBottomNavigationView();
        spinner = (ProgressBar)findViewById(R.id.mProgressBarSearch);
        spinner.setVisibility(View.VISIBLE);

        buttonChooseFilters = (Button)findViewById(R.id.mButtonFilters);
        buttonChooseFilters.setEnabled(false);

        itemClickListener = this;
        sortingTypeChooser = new SortingTypeChooser();
        sortingTypeChooser.setListToSort(pubs);
        setRecyclerView();
        httpRequests = new HttpRequests(this);

        if (googleServicesAvailable()) {
            manageLocation();
        } else {
            Toast.makeText(this, "There's no Google Services installed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view, int position) {
        final Pub currentPub = pubs.get(position);
        Intent i = new Intent(this, PubInfo.class);
        i.putExtra("placeID", currentPub.getPlaceID());
        startActivity(i);
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            sortingType = data.getStringExtra("sorting type");
            conveniences = data.getStringArrayListExtra("conveniences");
            Double newLongitude = data.getDoubleExtra("longitude", 0);
            Double newLatitude = data.getDoubleExtra("latitude", 0);
            if (newLongitude != 0 && newLatitude != 0 ) {
                latitude = newLatitude;
                longitude = newLongitude;
                newLocationSet = true;
            }
            else {
                newLocationSet = false;
            }
            String url;
            if (conveniences != null )
                url = httpRequests.getPubsWithConveniencesUrl(longitude,latitude, conveniences);
            else
                url = httpRequests.getNearbyPubsUrl(longitude,latitude);
            spinner.setVisibility(View.VISIBLE);
            buttonChooseFilters.setEnabled(false);
            manageGetPubsHttpConnection(url);
            pubs = sortingTypeChooser.getSortedList(sortingType);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void manageGetPubsHttpConnection(String url) {
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;

        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse(){

            @Override
            public void processFinish(String result, Boolean timeout){
                if (timeout) {
                    showAlert("Cannot connect to database. Try again later.");
                }
                else {
                    NearbyPubsParser parser = new NearbyPubsParser();
                    pubs = parser.parse(result);
                    if (pubs != null) {
                        if (pubs.size() == 0)
                            showAlert("No places found.");
                        setListAdapter();
                    } else {
                        showAlert("No places found.");
                    }
                }
                spinner.setVisibility(View.GONE);
                buttonChooseFilters.setEnabled(true);
            }
        }, new DownloadUrlWithGetMethod()).execute(dataTransfer);
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setListAdapter() {
        mAdapter = new PubListRecyclerViewerAdapter(pubs);
        recyclerView.setAdapter(mAdapter);
        sortingTypeChooser.setListToSort(pubs);
        mAdapter.setClickListener(itemClickListener);
        pubs = sortingTypeChooser.getSortedList(sortingType);
        mAdapter.notifyDataSetChanged();
    }

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
            public boolean onQueryTextChange(String newText) {{
                updateFilters(newText);
                return true;
            }}
            public void updateFilters(String newText) {
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(newText);
                }
            }
        });
    }

    public void mButtonFiltersOnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), Filters.class);
        startActivityForResult(myIntent, REQUEST_CODE);
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.my_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

    }

    private void setBottomNavigationView() {
        BottomNavigationView tabs =  findViewById(R.id.navigationtabs3);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_search).setChecked(true);
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
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
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

    private void manageLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        android.location.LocationListener mLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                if (!newLocationSet) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    //mozna sprawdzic czy lokalizacja zmienila sie o wiecej niz np 10m... ale nie wiem czy trzeba
                    String url;
                    if (conveniences == null || conveniences.size() == 0) {
                        url = httpRequests.getNearbyPubsUrl(longitude, latitude);
                    }
                    else {
                        url = httpRequests.getPubsWithConveniencesUrl(longitude,latitude, conveniences);
                    }
                    manageGetPubsHttpConnection(url);
                }
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
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10.0f, mLocationListener);
    }
}
