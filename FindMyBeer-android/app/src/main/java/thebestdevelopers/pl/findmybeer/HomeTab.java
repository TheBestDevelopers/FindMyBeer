package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubList.GetPubListElement;
import thebestdevelopers.pl.findmybeer.pubList.MyRecyclerViewerAdapter;
import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class HomeTab extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerViewerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Pub> pubs;
    public static double latitude, longitude;
    private List<HashMap<String, String>> nearbyPlaceList;

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

//        Object dataTransfer[] = new Object[1];
//        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
//        String url = getUrl(latitude, longitude, "pub");
//        dataTransfer[0] = url;
//        getNearbyPlacesData.execute(dataTransfer);
//        nearbyPlaceList = getNearbyPlacesData.getNearbyPlaceList();
//        if (nearbyPlaceList!= null)
//            Toast.makeText(getApplicationContext(), "list of pub created", Toast.LENGTH_SHORT).show();

    }

    private String getUrl(double lat, double lng, String nearby) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + lat + "," + lng);
        googlePlaceUrl.append("&radius=" + "5000");
        googlePlaceUrl.append("&type=" + nearby);
        googlePlaceUrl.append("&name=" + nearby);
        googlePlaceUrl.append("&key=" + "AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}

//package thebestdevelopers.pl.findmybeer;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//
//import java.util.ArrayList;
//
//import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
//import thebestdevelopers.pl.findmybeer.FavTab;
//import thebestdevelopers.pl.findmybeer.HomeTab;
//import thebestdevelopers.pl.findmybeer.ProfileTab;
//import thebestdevelopers.pl.findmybeer.mapsController.GetNearbyPlacesData;
//import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
//import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;
//import thebestdevelopers.pl.findmybeer.R;
//import thebestdevelopers.pl.findmybeer.SearchTab;
//import thebestdevelopers.pl.findmybeer.pubList.GetPubListElement;
//import thebestdevelopers.pl.findmybeer.pubList.MyRecyclerViewerAdapter;
//import thebestdevelopers.pl.findmybeer.pubList.Pub;
//
//public class HomeTab extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener, LocationListener,
//        GoogleMap.OnInfoWindowClickListener{
//
//    private RecyclerView recyclerView;
//    private MyRecyclerViewerAdapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Pub> pubs;
//    GoogleMap mGoogleMap;
//    GoogleApiClient mGoogleApiClient;
//    LocationRequest mLocationRequest;
//    double latitude, longitude;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hometab);
//
//        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        //recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//       // recyclerView.setHasFixedSize(true);
//
//        initializePubs();
//        mAdapter = new MyRecyclerViewerAdapter(pubs);
//        recyclerView.setAdapter(mAdapter);
//
//        if (googleServicesAvailable()) {
//            //setContentView(R.layout.activity_map_tab);
//            //initMap();
//        } else {
//            Toast.makeText(this, "There's no Google Services installed", Toast.LENGTH_LONG).show();
//        }
//        overridePendingTransition(0, 0);
//        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs1);
//        BottomNavigationViewHelper.disableShiftMode(tabs);
//        tabs.getMenu().findItem(R.id.action_home).setChecked(true);
//
//        Intent i;
//        tabs.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Intent i;
//                        switch (item.getItemId()) {
//                            case R.id.action_fav:
//                                i = new Intent(getApplicationContext(), FavTab.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(i);
//                                break;
//                            case R.id.action_search:
//                                i = new Intent(getApplicationContext(), SearchTab.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(i);
//                                break;
//                            case R.id.action_map:
//                                i = new Intent(getApplicationContext(), MapTab.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(i);
//                                break;
//                            case R.id.action_user:
//                                i = new Intent(getApplicationContext(), ProfileTab.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(i);
//                                break;
//                        }
//                        return true;
//                    }
//                });
//    }
//
//    public boolean googleServicesAvailable() {
//        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
//        int isAvailable = api.isGooglePlayServicesAvailable(this);
//        if (isAvailable == ConnectionResult.SUCCESS) {
//            return true;
//        } else if (api.isUserResolvableError(isAvailable)) {
//            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
//            dialog.show();
//        } else {
//            Toast.makeText(this, "Cannot connect to the play services", Toast.LENGTH_LONG).show();
//        }
//        return false;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Toast.makeText(this, "No access to the location services", Toast.LENGTH_LONG).show();
//            return;
//        }
//        mGoogleMap.setMyLocationEnabled(true);
//        mGoogleMap.setOnInfoWindowClickListener(this);
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        //mLocationRequest.setInterval(120000); //after 2 minutes move camera to the user location on map
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if(location == null) {
//            Toast.makeText(this, "No access to the location services", Toast.LENGTH_LONG).show();
//        } else {
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
//            LatLng ll = new LatLng(latitude, longitude);
//            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
//            mGoogleMap.animateCamera(update);
//        }
//    }
//
////    public void onClick(View v) {
////        mGoogleMap.clear();
////        Object dataTransfer[] = new Object[2];
////        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
////        String url = getUrl(latitude, longitude, "pub");
////        dataTransfer[0] = mGoogleMap;
////        dataTransfer[1] = url;
////        getNearbyPlacesData.execute(dataTransfer);
////    }
//    private void initializePubs() {
//        pubs = new ArrayList<>();
//
//
//        Object dataTransfer[] = new Object[1];
//        String url = getUrl(latitude, longitude, "pub");
//        dataTransfer[0] = url;
//        GetPubListElement getPubListElement =  new GetPubListElement(this);
//        getPubListElement.setPubSArray(pubs);
//        getPubListElement.execute(dataTransfer);
//
//
//    }
//
//    private String getUrl(double lat, double lng, String nearby) {
//        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googlePlaceUrl.append("location="+lat+","+lng);
//        googlePlaceUrl.append("&radius="+"5000");
//        googlePlaceUrl.append("&type="+nearby);
//        googlePlaceUrl.append("&name="+nearby);
//        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
//        Log.d("created url", googlePlaceUrl.toString());
//        return googlePlaceUrl.toString();
//    }
//
//    @Override
//    public void onInfoWindowClick(Marker marker) {
////        Intent myIntent = new Intent(getApplicationContext(), PubInfo.class);
////        String id = marker.getSnippet();
////        myIntent.putExtra("placeID", id);
////        startActivity(myIntent);
//    }
//}

