package thebestdevelopers.pl.findmybeer.profileController;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.Login;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;

public class ProfileTab extends AppCompatActivity {

    TextView mLoginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile_tab);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*
        TextView txt = (TextView) findViewById(R.id.tLogin);
        txt.setVisibility(View.GONE);
        Button btn = (Button) findViewById(R.id.bLogOut);
        btn.setVisibility(View.GONE);
        Button btn2 = (Button) findViewById(R.id.bChange);
        btn2.setVisibility(View.GONE);
        Button btn3 = (Button) findViewById(R.id.bDelete);
        btn3.setVisibility(View.GONE);
        Button btn4 = (Button) findViewById(R.id.bTemp);
        btn4.setVisibility(View.GONE);
        */
        ProgressBar spinner = (ProgressBar)findViewById(R.id.mProgressBarHome);
        spinner.setVisibility(View.GONE);


        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs5);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_user).setChecked(true);
        Intent i;

        mLoginText = (TextView)findViewById(R.id.tLogin);
        mLoginText.setText("User name");

        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                i = new Intent(getApplicationContext(), HomeTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_search:
                                i = new Intent(getApplicationContext(), SearchTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.action_map:
                                i = new Intent(getApplicationContext(), MapTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                finish();
                                break;
                        }

                        return true;
                    }
                });
    }

    public void mButtonLogOutOnClick(View v){
        //obsluga wylogowania
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //temp - potem wylogowanie
                        Intent myIntent = new Intent(getApplicationContext(), Login.class);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void mButtonChangePswOnClick(View v){
        //obsluga zmiany hasla
        Intent myIntent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(myIntent);
    }

    public void mButtonTemp(View v){
        //obsluga zmiany hasla
        Intent myIntent = new Intent(getApplicationContext(), PubDetails.class);
        startActivity(myIntent);
    }

    public void mButtonDeleteOnClick(View v){
        //obsluga usuniecia konta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //temp - potem usuniecie konta
                        Intent myIntent = new Intent(getApplicationContext(), Login.class);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //url do usuniecia konta
    private String getUrl(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
    //url do wylogowania
    private String getUrl2(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        googlePlaceUrl.append(id);
        googlePlaceUrl.append("&key="+"AIzaSyB3iQRgruru1jotumbRTuzOYiWSePz41ZQ");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
