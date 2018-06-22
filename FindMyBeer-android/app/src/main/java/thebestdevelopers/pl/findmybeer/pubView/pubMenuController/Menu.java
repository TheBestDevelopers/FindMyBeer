package thebestdevelopers.pl.findmybeer.pubView.pubMenuController;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class Menu extends AppCompatActivity {


    String mId, mName, mAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pub);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BottomNavigationView tabs = findViewById(R.id.navigationtabs);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_home).setChecked(true);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            mId = (String) b.get("placeID");
        }

        String url = getUrl(mId);
        GetJsonResult getMenuData = new GetJsonResult(this, getApplicationContext(), "GET");
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        getMenuData.execute(dataTransfer);

        Intent i;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                i = new Intent(getApplicationContext(), PubDetails.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_edit:
                                i = new Intent(getApplicationContext(), PubEdit.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_user:
                                i = new Intent(getApplicationContext(), ProfileTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                        }
                        return true;
                    }
                });
    }

    private String getUrl(String id) {
        StringBuilder menuUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        menuUrl.append("/api/pubs/getPubMenuForPubs");
        Log.d("created url", menuUrl.toString());
        return menuUrl.toString();
    }
}
