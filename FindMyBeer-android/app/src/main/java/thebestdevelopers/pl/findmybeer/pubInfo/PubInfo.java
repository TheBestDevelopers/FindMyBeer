package thebestdevelopers.pl.findmybeer.pubInfo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import thebestdevelopers.pl.findmybeer.R;

public class PubInfo extends AppCompatActivity {

    public TextView mName, mRating, mAddress, mPhone, mWebsite;
    public Button mAddFav, mDirections, mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(0, 0);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            String id = (String) b.get("placeID"); //wczytanie id miejsca oznaczonego markerem
            String url = getUrl(id);
            GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
            Object dataTransfer[] = new Object[1];
            dataTransfer[0] = url;
            getNearbyPlacesData.execute(dataTransfer);
        }
    }

    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
