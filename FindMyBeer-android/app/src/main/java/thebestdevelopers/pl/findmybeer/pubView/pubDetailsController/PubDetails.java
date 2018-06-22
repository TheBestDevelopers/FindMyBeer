package thebestdevelopers.pl.findmybeer.pubView.pubDetailsController;

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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;
import thebestdevelopers.pl.findmybeer.pubView.pubMenuController.Menu;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class PubDetails extends AppCompatActivity {

    public TextView mName, mAddress;
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_details);

        ScrollView v = findViewById(R.id.bView);
        v.setVisibility(View.GONE);

        ProgressBar spinner = findViewById(R.id.mProgressBarProfile);
        spinner.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = findViewById(R.id.navigationtabs);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_home).setChecked(true);

        //pobranie jsona
        id = "13";
        String url = getUrl(id);
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this, getApplicationContext());
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        getNearbyPlacesData.execute(dataTransfer);

        Intent temp;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent temp;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                //temp = new Intent(getApplicationContext(), PubDetails.class);
                                //temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                //startActivity(temp);
                               // finish();
                                break;
                            case R.id.action_edit:
                                temp = new Intent(getApplicationContext(), PubEdit.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                finish();
                                break;
                            case R.id.action_user:
                                temp = new Intent(getApplicationContext(), ProfileTab.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                finish();
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
        mName = findViewById(R.id.tName);
        mAddress = findViewById(R.id.tAddress);
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

    public void mButtonMenuClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), Menu.class);
        myIntent.putExtra("placeID", id);
        startActivity(myIntent);
    }

    //http://localhost:8080/api/pubs/getPubView?pubID=13
    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/getPubView");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
