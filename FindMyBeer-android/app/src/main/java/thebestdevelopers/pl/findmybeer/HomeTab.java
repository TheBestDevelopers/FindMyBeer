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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
import thebestdevelopers.pl.findmybeer.pubList.ItemClickListener;
import thebestdevelopers.pl.findmybeer.pubList.MainMenuRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.pubList.Pub;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;
import thebestdevelopers.pl.findmybeer.searchController.SortingTypeChooser;


public class HomeTab extends AppCompatActivity implements ItemClickListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private MainMenuRecyclerViewerAdapter mAdapter;
    private ArrayList<Pub> pubs = new ArrayList<>();
    MockPubsData mockPubsData;
    SortingTypeChooser sortingTypeChooser;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometab);
        overridePendingTransition(0, 0);
        setBottomNavigationView();

        spinner = (ProgressBar)findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.VISIBLE);
        mockPubsData = new MockPubsData();
        mGoogleApiClient = new GoogleApiClient.Builder(HomeTab.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        for (int i = 0 ; i< mockPubsData.getPubs().size(); ++i) {
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, mockPubsData.getPubs().get(i).getPlaceID());
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }


        setRecyclerView();
        sortingTypeChooser = new SortingTypeChooser(pubs);
        if (googleServicesAvailable()) {
            manageLocation();
        } else {
            Toast.makeText(this, "There's no Google Services installed", Toast.LENGTH_LONG).show();
        }
    }
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                return;
            }
            final Place place = places.get(0);
            LatLng placeLatLong = place.getLatLng();
            Double latitude = placeLatLong.latitude;
            Double longitude = placeLatLong.longitude;
            String pubName = place.getName().toString();
            Float rating = place.getRating();
            String id = place.getId();
            Integer freeTablesCount = mockPubsData.getFreeTablesCount(id);
            Pub pub = new Pub(pubName, latitude, longitude, freeTablesCount, rating, id);
            pubs.add(pub);
            spinner.setVisibility(View.GONE);
            setRecyclerView();
            sortingTypeChooser = new SortingTypeChooser(pubs);
            mAdapter.notifyDataSetChanged();
        }
    };

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

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mAdapter = new MainMenuRecyclerViewerAdapter(pubs);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
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
                mAdapter.updateLocation(location);
                pubs = sortingTypeChooser.getSortedList("distance ascending");
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
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10.0f, mLocationListener);
    }
}

