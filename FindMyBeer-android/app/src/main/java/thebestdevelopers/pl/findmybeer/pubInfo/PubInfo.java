package thebestdevelopers.pl.findmybeer.pubInfo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.favController.PubData;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.menuController.Menu;
import thebestdevelopers.pl.findmybeer.profileController.ProfileTab;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;

public class PubInfo extends AppCompatActivity {

    public TextView mName, mRating, mAddress, mPhone, mWebsite;
    String id, id2;
    public ProgressBar spinner;
    public ScrollView v;
    private ArrayList<PubData> mFavList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_info);
        v = (ScrollView) findViewById(R.id.bView);
        v.setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs7);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_map).setChecked(true);

        mName = (TextView)findViewById(R.id.tName);
        mAddress = (TextView)findViewById(R.id.tAddress);

        spinner = (ProgressBar)findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.VISIBLE);


        TextView txt2 = (TextView) findViewById(R.id.tError);
        txt2.setVisibility(View.GONE);

        //wczytanie info po wcisnieciu markera na mapie.
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String url;
        if (b != null) {
            id = (String) b.get("placeID"); //wczytanie id miejsca oznaczonego markerem
            if(id.charAt(0) == '!' && id.charAt(1) == '!' && id.charAt(2) == '!') { //puby z naszej bazy maja na poczatku id trzy wykrzykniki, aby latwiej rozpoznawac
                id2 = id.substring(3,id.length());
                url = getUrl2(id2);
            }
            else
                url = getUrl(id);

            GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
            Object dataTransfer[] = new Object[1];
            dataTransfer[0] = url;
            getNearbyPlacesData.execute(dataTransfer);
        }

        Intent temp;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent temp;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                temp = new Intent(getApplicationContext(), HomeTab.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_search:
                                temp = new Intent(getApplicationContext(), SearchTab.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_fav:
                                temp = new Intent(getApplicationContext(), FavTab.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_map:
                                temp = new Intent(getApplicationContext(), MapTab.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_user:
                                temp = new Intent(getApplicationContext(), ProfileTab.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                        }
                        return true;
                    }
                });
    }

    /**
     * Methode that redirect user to Google Maps turn-by-turn navigation.
     * @param v
     */
    public void mButtonGetDirectionsClick(View v){
        mName = (TextView)findViewById(R.id.tName);
        mAddress = (TextView)findViewById(R.id.tAddress);
        String name =  mName.getText().toString();
        String address = mAddress.getText().toString();
        if ((name!= null || !name.equals("")) && (address!= null || !address.equals("") )) {
            name = name.replace(" ", "+");
            address = name.replace(" ", "+");
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+name+",+"+address);
            Log.d("maps redirect", gmmIntentUri.toString());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    /**
     * Methode that adds place to favourites.
     * @param v
     *
     */
    public void mButtonAddFavClick(View v) {
        //przekazanie id miejsca
        //id
        //przeslanie do bazy
        Button btn = (Button) findViewById(R.id.bAddFav);
        String text = btn.getText().toString();

        if (text.equals("Remove from favourites")) {
            GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
            Object dataTransfer[] = new Object[2];
            String url = getUrl3(id2);
            dataTransfer[0] = url;
            dataTransfer[1] = "remove";
            getNearbyPlacesData.execute(dataTransfer);
        } else if (text.equals("Add to favourites")) {
            GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
            Object dataTransfer[] = new Object[2];
            String url = getUrl4(id2);
            dataTransfer[0] = url;
            dataTransfer[1] = "add";
            getNearbyPlacesData.execute(dataTransfer);
        }
        //wyslanie linku do bazy
        /*
        Intent myIntent = new Intent(getApplicationContext(), FavTab.class);
        myIntent.putExtra("placeID", id);
        myIntent.putExtra("placeName", mName.getText().toString());
        myIntent.putExtra("placeAddress", mAddress.getText().toString());
        startActivity(myIntent);
        */
    }

    public void mButtonMenuClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), Menu.class);
        myIntent.putExtra("placeID", id2);
        startActivity(myIntent);
    }

    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }

    //http://localhost:8080/api/pubs/getPubInfo?userID=2&pubID=3
    private String getUrl2(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/getPubInfo?userID="+"8"); //zamiast 2 ma byc user id
        googlePlaceUrl.append("&pubID="+id);
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }

    //http://localhost:8080/api/favourites/addFavourite?userID=9&pubID=13
    private String getUrl3(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/favourites/addFavourite?userID="+"9"); //zamiast 2 ma byc user id
        googlePlaceUrl.append("&pubID="+id);
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
    //http://localhost:8080/api/favourites/deleteFavourite?pubID=00000&userID=12344
    private String getUrl4(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/favourites/deleteFavourite?pubID="+id); //zamiast 2 ma byc user id
        googlePlaceUrl.append("&userID="+"9");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}


