package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditTables;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class editTables extends AppCompatActivity {

    String id;
    public NumberPicker np1, np2, np4, np6, np8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tables);

        ScrollView v = (ScrollView) findViewById(R.id.bView);
        v.setVisibility(View.GONE);

        ProgressBar spinner = (ProgressBar)findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_edit).setChecked(true);

        np1 = findViewById(R.id.numberPicker1);
        np1.setMinValue(0);
        np1.setMaxValue(100);

        np2 = findViewById(R.id.numberPicker2);
        np2.setMinValue(0);
        np2.setMaxValue(100);

        np4 = findViewById(R.id.numberPicker4);
        np4.setMinValue(0);
        np4.setMaxValue(100);

        np6 = findViewById(R.id.numberPicker6);
        np6.setMinValue(0);
        np6.setMaxValue(100);

        np8 = findViewById(R.id.numberPicker8);
        np8.setMinValue(0);
        np8.setMaxValue(100);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("placeID");
        }

        String url = getUrl(id);
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
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
                                temp = new Intent(getApplicationContext(), PubDetails.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_edit:
                                temp = new Intent(getApplicationContext(), PubEdit.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                            case R.id.action_user:
                                temp = new Intent(getApplicationContext(), ProfileTab.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                break;
                        }
                        finish();
                        return true;
                    }
                });
    }

    public void mButtonChangeClick(View v) {
        //obsluga
    }

    //http://localhost:8080/api/pubs/getPubInfo?userID=2&pubID=3
    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/getPubInfo?userID="+"8"); //zamiast 2 ma byc user id
        googlePlaceUrl.append("&pubID="+id);
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
