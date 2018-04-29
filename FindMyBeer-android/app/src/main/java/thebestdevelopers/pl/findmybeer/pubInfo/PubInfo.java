package thebestdevelopers.pl.findmybeer.pubInfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.favController.PubData;
import thebestdevelopers.pl.findmybeer.menuController.Menu;

public class PubInfo extends AppCompatActivity {

    public TextView mName, mRating, mAddress, mPhone, mWebsite;
    String id;
    private ArrayList<PubData> mFavList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(0, 0);

        mName = (TextView)findViewById(R.id.tName);
        mAddress = (TextView)findViewById(R.id.tAddress);

        //wczytanie info po wcisnieciu markera na mapie.
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("placeID"); //wczytanie id miejsca oznaczonego markerem
            String url = getUrl(id);
            GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
            Object dataTransfer[] = new Object[1];
            dataTransfer[0] = url;
            getNearbyPlacesData.execute(dataTransfer);
        }
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
     */
    public void mButtonAddFavClick(View v) {
        //przekazanie id miejsca
        //id
        //przeslanie do bazy
        Intent myIntent = new Intent(getApplicationContext(), FavTab.class);
        myIntent.putExtra("placeID", id);
        myIntent.putExtra("placeName", mName.getText().toString());
        myIntent.putExtra("placeAddress", mAddress.getText().toString());
        startActivity(myIntent);
    }

    public void mButtonMenuClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), Menu.class);
        myIntent.putExtra("placeID", id);
        myIntent.putExtra("placeName", mName.getText().toString());
        myIntent.putExtra("placeAddress", mAddress.getText().toString());
        startActivity(myIntent);
    }

    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}


