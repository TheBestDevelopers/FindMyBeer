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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.loginController.Login;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlWithGetMethod;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.pubListController.ItemClickListener;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.pubListController.NearbyPubsParser;
import thebestdevelopers.pl.findmybeer.pubListController.Pub;
import thebestdevelopers.pl.findmybeer.pubListController.PubListRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypeChooser;

public class HomeTab extends AppCompatActivity implements ItemClickListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    public ProgressBar spinner;
    Double latitude = 0.0;
    Double longitude = 0.0;
    ArrayList<Pub> pubs;
    private PubListRecyclerViewerAdapter mAdapter;
    SortingTypeChooser  sortingTypeChooser;
    RecyclerView recyclerView;
    ItemClickListener itemClickListener;
    HttpRequests httpRequests;
    private TextView mErrorTextView;
    private SessionController sessionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometab);
        overridePendingTransition(0, 0);

        sessionController = new SessionController(getApplicationContext());
        sessionController.checkLogin();

        setBottomNavigationView();
        itemClickListener = this;
        spinner = findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.VISIBLE);
        sortingTypeChooser = new SortingTypeChooser();
        httpRequests = new HttpRequests(this);
        mErrorTextView = findViewById(R.id.mErrorTextView);

        setRecyclerView();

        if (googleServicesAvailable()) {
            manageLocation();
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
    public void onClick(View view, int position) {
        final Pub currentPub = pubs.get(position);
        Intent i = new Intent(this, PubInfo.class);
        i.putExtra("placeID", currentPub.getPlaceID());
        startActivity(i);
    }

    private void setBottomNavigationView() {
        BottomNavigationView tabs = findViewById(R.id.navigationtabs1);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_home).setChecked(true);
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

    private void manageLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        android.location.LocationListener mLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                manageHttpConnection();
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

            private void manageHttpConnection() {
                String url = httpRequests.getNearbyPubsUrl(longitude, latitude);
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
                                showAlert("");
                            } else {
                                showAlert("No places found.");
                            }
                        }
                        spinner.setVisibility(View.GONE);
                    }
                }, new DownloadUrlWithGetMethod(getApplicationContext())).execute(dataTransfer);
            }

            private void showAlert(String message) {
                mErrorTextView.setText(message);
            }


            private void setListAdapter() {
                mAdapter = new PubListRecyclerViewerAdapter(pubs);
                recyclerView.setAdapter(mAdapter);
                sortingTypeChooser.setListToSort(pubs);
                mAdapter.setClickListener(itemClickListener);
                pubs = sortingTypeChooser.getSortedList("distance ascending");
                mAdapter.notifyDataSetChanged();
            }

        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10.0f, mLocationListener);
    }


}

