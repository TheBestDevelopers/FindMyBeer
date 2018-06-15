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
    boolean c1,c2,c3,c4,c5,c6,c7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_conveniences);

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

        //pobranie jsona i ustawienie checkboxów
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

        CheckBox chbx1 = (CheckBox) findViewById(R.id.checkbox_wifi);
        CheckBox chbx2 = (CheckBox) findViewById(R.id.checkbox_adapted_disabled);
        CheckBox chbx3 = (CheckBox) findViewById(R.id.checkbox_boardgames);
        CheckBox chbx4 = (CheckBox) findViewById(R.id.checkbox_discountsforgroups);
        CheckBox chbx5 = (CheckBox) findViewById(R.id.checkbox_discountsforstudents);
        CheckBox chbx6 = (CheckBox) findViewById(R.id.checkbox_roastingroom);
        CheckBox chbx7 = (CheckBox) findViewById(R.id.checkbox_toilet);
        c1 = chbx1.isChecked();
        c2 = chbx2.isChecked();
        c3 = chbx3.isChecked();
        c4 = chbx4.isChecked();
        c5 = chbx5.isChecked();
        c6 = chbx6.isChecked();
        c7 = chbx7.isChecked();

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
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this);
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
        googlePlaceUrl.append("/api/pubs/getPubView?pubID=");
        googlePlaceUrl.append(id);
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
    //setConveniences http://localhost:8080/api/pubs/setConveniences?pubID=1234&true=boardgames,wifi&false=toilet
    //http://localhost:8080/api/pubs/setConveniences?pubID=13&true&false=TOILET
    private String getUrl2(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP)); //temp
        //TO DO
        googlePlaceUrl.append("/api/pubs/setConveniences?pubID=");
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&true");

        CheckBox chbx1 = (CheckBox) findViewById(R.id.checkbox_wifi);
        CheckBox chbx2 = (CheckBox) findViewById(R.id.checkbox_adapted_disabled);
        CheckBox chbx3 = (CheckBox) findViewById(R.id.checkbox_boardgames);
        CheckBox chbx4 = (CheckBox) findViewById(R.id.checkbox_discountsforgroups);
        CheckBox chbx5 = (CheckBox) findViewById(R.id.checkbox_discountsforstudents);
        CheckBox chbx6 = (CheckBox) findViewById(R.id.checkbox_roastingroom);
        CheckBox chbx7 = (CheckBox) findViewById(R.id.checkbox_toilet);
        boolean checkTrue = false;
        boolean checkFalse = false;
        /*if (((chbx1.isChecked() != c1) && (chbx1.isChecked() == true)) || ((chbx2.isChecked() != c2) && (chbx2.isChecked() == true))
                || ((chbx3.isChecked() != c3) && (chbx3.isChecked() == true)) || ((chbx4.isChecked() != c4) && (chbx4.isChecked() == true))
                || ((chbx5.isChecked() != c5) && (chbx5.isChecked() == true)) || ((chbx6.isChecked() != c6) && (chbx6.isChecked() == true))
                || ((chbx7.isChecked() != c7) && (chbx7.isChecked() == true)))
            checkTrue=true;

        if (((chbx1.isChecked() != c1) && (chbx1.isChecked() == false)) || ((chbx2.isChecked() != c2) && (chbx2.isChecked() == false))
                || ((chbx3.isChecked() != c3) && (chbx3.isChecked() == false)) || ((chbx4.isChecked() != c4) && (chbx4.isChecked() == false))
                || ((chbx5.isChecked() != c5) && (chbx5.isChecked() == false)) || ((chbx6.isChecked() != c6) && (chbx6.isChecked() == false))
                || ((chbx7.isChecked() != c7) && (chbx7.isChecked() == false)))
            checkFalse=true;

        if (checkTrue) {
            googlePlaceUrl.append("=");
            if (chbx1.isChecked())
                googlePlaceUrl.append("WI-FI");
            if (chbx2.isChecked())
                googlePlaceUrl.append(",FACILITIES_FOR_THE_DISABLED");
            if (chbx3.isChecked())
                googlePlaceUrl.append(",BOARD_GAMES");
            if (chbx4.isChecked())
                googlePlaceUrl.append(",DISCOUNTS_FOR_STUDENTS");
            if (chbx5.isChecked())
                googlePlaceUrl.append(",DISCOUNTS_FOR_STUDENTS");
            if (chbx6.isChecked())
                googlePlaceUrl.append(",ROASTING_ROOM");
            if (chbx7.isChecked())
                googlePlaceUrl.append(",TOILET");
        }
        googlePlaceUrl.append("&false");
        if (checkFalse) {
            googlePlaceUrl.append("=");
            if (chbx1.isChecked())
                googlePlaceUrl.append("WI-FI");
            if (chbx2.isChecked())
                googlePlaceUrl.append(",FACILITIES_FOR_THE_DISABLED");
            if (chbx3.isChecked())
                googlePlaceUrl.append(",BOARD_GAMES");
            if (chbx4.isChecked())
                googlePlaceUrl.append(",DISCOUNTS_FOR_STUDENTS");
            if (chbx5.isChecked())
                googlePlaceUrl.append(",DISCOUNTS_FOR_STUDENTS");
            if (chbx6.isChecked())
                googlePlaceUrl.append(",ROASTING_ROOM");
            if (chbx7.isChecked())
                googlePlaceUrl.append(",TOILET");
        }
        */
/*
        if (chbx1.isChecked() || chbx2.isChecked() || chbx3.isChecked() || chbx4.isChecked() || chbx5.isChecked()
                || chbx6.isChecked() || chbx7.isChecked()) {
            checkTrue = true;
        }
        if (!chbx1.isChecked() || !chbx2.isChecked() || !chbx3.isChecked() || !chbx4.isChecked() || !chbx5.isChecked()
                || !chbx6.isChecked() || !chbx7.isChecked()) {
            checkFalse = true;
        }

        if (checkTrue) {
            googlePlaceUrl.append("=");
            if (chbx1.isChecked())
                googlePlaceUrl.append("WI-FI,");
            if (chbx2.isChecked())
                googlePlaceUrl.append("FACILITIES_FOR_THE_DISABLED,");
            if (chbx3.isChecked())
                googlePlaceUrl.append("BOARD_GAMES,");
            if (chbx5.isChecked())
                googlePlaceUrl.append("DISCOUNTS_FOR_STUDENTS");
            if (chbx6.isChecked())
                googlePlaceUrl.append("ROASTING_ROOM,");
            if (chbx7.isChecked())
                googlePlaceUrl.append("TOILET");
        }

        googlePlaceUrl.append("&false");
        if (checkFalse) {
            googlePlaceUrl.append("=");
            if (!chbx1.isChecked())
                googlePlaceUrl.append("WI-FI,");
            if (!chbx2.isChecked())
                googlePlaceUrl.append("FACILITIES_FOR_THE_DISABLED,");
            if (!chbx3.isChecked())
                googlePlaceUrl.append("BOARD_GAMES,");
            if (!chbx5.isChecked())
                googlePlaceUrl.append("DISCOUNTS_FOR_STUDENTS,");
            if (!chbx6.isChecked())
                googlePlaceUrl.append("ROASTING_ROOM,");
            if (!chbx7.isChecked())
                googlePlaceUrl.append("TOILET");
        }

*/
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
