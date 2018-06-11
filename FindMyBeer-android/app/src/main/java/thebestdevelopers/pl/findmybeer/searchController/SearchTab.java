package thebestdevelopers.pl.findmybeer.searchController;

import android.app.Activity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubListController.GetNearbyPubsTask;
import thebestdevelopers.pl.findmybeer.pubListController.PubListRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.pubListController.Pub;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypeChooser;

public class SearchTab extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks  {

    private final int REQUEST_CODE = 0;
    private PubListRecyclerViewerAdapter mAdapter;
    public ArrayList<Pub> pubs = new ArrayList<>();
    private String sortingType = "distance ascending";
    SortingTypeChooser sortingTypeChooser;
    ArrayList<String> conveniences;
    private double longitude, latitude;
    private ProgressBar spinner;
    GetNearbyPubsTask getPubsData = null;
    Boolean newLocationSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tab);
        overridePendingTransition(0, 0);
        setBottomNavigationView();
        spinner = (ProgressBar)findViewById(R.id.mProgressBarSearch);
        spinner.setVisibility(View.VISIBLE);
        if (googleServicesAvailable()) {
            manageLocation(null);
        } else {
            Toast.makeText(this, "There's no Google Services installed", Toast.LENGTH_LONG).show();
        }
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
            longitude = data.getDoubleExtra("longitude", 0);
            latitude = data.getDoubleExtra("latitude", 0);
            if (latitude != 0 && longitude != 0 ) {
                Location location = new Location("");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                newLocationSet = true;
                manageLocation(location);

            }
            else {
                newLocationSet = false;
                manageLocation(null);
            }
            pubs = sortingTypeChooser.getSortedList(sortingType);
            mAdapter.notifyDataSetChanged();
        }
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
            public boolean onQueryTextChange(String newText) {
                if (getPubsData != null) {
                    getPubsData.updateFilters(newText); //if user writes before api is loaded, the filter will not work until he changes it
                                                        //when pubs are listed
                }
                return true;
            }
        });
    }

    public void mButtonFiltersOnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), Filters.class);
        startActivityForResult(myIntent, REQUEST_CODE);
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
                        finish();
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

    private void manageLocation(final Location chosenLocation) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Activity activity = this;
        android.location.LocationListener mLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                Location setLocation = null;
                if (chosenLocation != null && newLocationSet) {
                    setLocation = chosenLocation;
                }
                else if (!newLocationSet) {
                    setLocation = location;
                }
                if (setLocation != null) {
                    longitude = setLocation.getLongitude();
                    latitude = setLocation.getLatitude();
                    manageHttpConnection(setLocation);
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

            private void manageHttpConnection(Location location) {
                String url = getUrl();
                getPubsData = new GetNearbyPubsTask(activity, spinner, location);
                Object dataTransfer[] = new Object[1];
                dataTransfer[0] = url;
                getPubsData.execute(dataTransfer);
            }

            private String getUrl() {
                StringBuilder menuUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
                menuUrl.append("/api/pubs/getNearestPubs?longitude=");
                menuUrl.append(longitude);
                menuUrl.append("&latitude=");
                menuUrl.append(latitude);
                Log.d("created url", menuUrl.toString());
                return menuUrl.toString();
            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10.0f, mLocationListener);
    }
}
