package thebestdevelopers.pl.findmybeer.pubView.pubEditController.pubEditMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;
import thebestdevelopers.pl.findmybeer.pubView.pubProfileController.ProfileTab;

public class editMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = findViewById(R.id.navigationtabs);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        overridePendingTransition(0, 0);
        tabs.getMenu().findItem(R.id.action_edit).setChecked(true);

        Button btn = findViewById(R.id.bChange);
        btn.setClickable(false);

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
                                temp = new Intent(getApplicationContext(), PubEdit.class);
                                temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(temp);
                                finish();
                                break;
                            case R.id.action_user:
                                temp = new Intent(getApplicationContext(), ProfileTab.class);
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
        //obsluga
    }
}
