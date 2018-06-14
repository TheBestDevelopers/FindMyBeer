package thebestdevelopers.pl.findmybeer.pubView.pubEditController;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditConveniences.editConveniences;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditMenu.editMenu;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditTables.editTables;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class PubEdit extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_edit).setChecked(true);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("placeID");
        }

        Intent temp;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent temp;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                temp = new Intent(getApplicationContext(), PubDetails.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                finish();
                                break;
                            case R.id.action_edit:
                                //temp = new Intent(getApplicationContext(), PubEdit.class);
                                //temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                //startActivity(temp);
                                // finish();
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

    public void mButtonMenuClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), editMenu.class);
        //myIntent.putExtra("placeID", id);
        startActivity(myIntent);
    }

    public void mButtonConvsClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), editConveniences.class);
        myIntent.putExtra("placeID", id);
        startActivity(myIntent);
    }

    public void mButtonTablesClick(View v) {
        Intent myIntent = new Intent(getApplicationContext(), editTables.class);
        myIntent.putExtra("placeID", id);
        startActivity(myIntent);
    }
}
