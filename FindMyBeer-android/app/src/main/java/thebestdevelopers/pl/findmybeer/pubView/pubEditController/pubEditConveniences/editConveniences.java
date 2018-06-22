package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditConveniences;

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
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class editConveniences extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_conveniences);

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
        tabs.getMenu().findItem(R.id.action_edit).setChecked(true);

        //pobranie jsona i ustawienie checkboxów
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("placeID");
        }

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
                                temp = new Intent(getApplicationContext(), PubDetails.class);
                                temp.putExtra("placeID", id);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                finish();
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

    public void mButtonChangeClick(View v) {
        //pobranie checkboxów i wysłanie zapytania na serwer
        String url = getUrl2(id);
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this, getApplicationContext());
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = url;
        dataTransfer[1] = "change";
        getNearbyPlacesData.execute(dataTransfer);

        Intent temp = new Intent(getApplicationContext(), PubEdit.class);
        temp.putExtra("placeID", id);
        temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(temp);
        finish();
    }

    //http://localhost:8080/api/pubs/getPubInfo?userID=2&pubID=3
    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/getPubView");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
    //http://localhost:8080/api/pubs/setConveniences?pubID=13&WI-FI=true&TOILET=true&ROASTING_ROOM=false&DISCOUNTS_FOR_STUDENTS=true&FACILITIES_FOR_THE_DISABLED=false&BOARD_GAMES=false
    private String getUrl2(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/setConveniences?");

        CheckBox chbx1 = findViewById(R.id.checkbox_wifi);
        CheckBox chbx2 = findViewById(R.id.checkbox_adapted_disabled);
        CheckBox chbx3 = findViewById(R.id.checkbox_boardgames);
        CheckBox chbx4 = findViewById(R.id.checkbox_discountsforgroups);
        CheckBox chbx5 = findViewById(R.id.checkbox_discountsforstudents);
        CheckBox chbx6 = findViewById(R.id.checkbox_roastingroom);
        CheckBox chbx7 = findViewById(R.id.checkbox_toilet);

        googlePlaceUrl.append("WI-FI=" + chbx1.isChecked());
        googlePlaceUrl.append("&TOILET=" + chbx7.isChecked());
        googlePlaceUrl.append("&ROASTING_ROOM=" + chbx6.isChecked());
        googlePlaceUrl.append("&DISCOUNTS_FOR_STUDENTS=" + chbx5.isChecked());
        googlePlaceUrl.append("&FACILITIES_FOR_THE_DISABLED=" + chbx2.isChecked());
        googlePlaceUrl.append("&BOARD_GAMES=" + chbx3.isChecked());

        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
