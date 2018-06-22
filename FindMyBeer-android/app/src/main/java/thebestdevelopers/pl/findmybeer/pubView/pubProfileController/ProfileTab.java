package thebestdevelopers.pl.findmybeer.pubView.pubProfileController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.SessionController;
import thebestdevelopers.pl.findmybeer.loginController.Login;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;
import thebestdevelopers.pl.findmybeer.pubView.pubEditController.PubEdit;

public class ProfileTab extends AppCompatActivity {

    TextView mLoginText;
    private SessionController sessionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile_pub);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = findViewById(R.id.navigationtabs5);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_user).setChecked(true);
        sessionController = new SessionController(getApplicationContext());

        String id = "8";
        String url = getUrl3(id);
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this, getApplicationContext());
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        getNearbyPlacesData.execute(dataTransfer);

        Intent i;

        mLoginText = findViewById(R.id.tLogin);
        mLoginText.setText("User name");
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
                                //temp = new Intent(getApplicationContext(), ProfileTab.class);
                                //temp.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                //startActivity(temp);
                                //finish();
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
                        sessionController.logoutUser();
                        finish();
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
        Intent myIntent = new Intent(getApplicationContext(), thebestdevelopers.pl.findmybeer.profileController.ChangePasswordActivity.class);
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

    //http://localhost:8080/api/users/getUsername?ID=8
    private String getUrl3(String id) {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        googlePlaceUrl.append("/api/users/getUsername");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
