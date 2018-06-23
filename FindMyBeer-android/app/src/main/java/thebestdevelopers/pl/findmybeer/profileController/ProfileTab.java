package thebestdevelopers.pl.findmybeer.profileController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlWithoutJSONBody;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.SessionController;
import thebestdevelopers.pl.findmybeer.favController.FavTab;
import thebestdevelopers.pl.findmybeer.mapsController.MapTab;
import thebestdevelopers.pl.findmybeer.searchController.SearchTab;

public class ProfileTab extends AppCompatActivity {

    TextView mLoginText;
    private SessionController sessionController;
    HttpRequests httpRequests;
    String password;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile_tab);

        sessionController = new SessionController(getApplicationContext());
        httpRequests = new HttpRequests(this);
        spinner = findViewById(R.id.mProgressBarProfile);

        spinner.setVisibility(View.VISIBLE);
        TextView mName = findViewById(R.id.tLogin);
        mName.setVisibility(View.GONE);
        TextView mError = findViewById(R.id.tError);
        mError.setVisibility(View.GONE);

        overridePendingTransition(0, 0);
        BottomNavigationView tabs = findViewById(R.id.navigationtabs5);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_user).setChecked(true);
        String url = getUrl3();
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this, getApplicationContext(), "GET");
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        getNearbyPlacesData.execute(dataTransfer);

        Intent i;
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

    public void mButtonLogOutOnClick(View v) {
        //obsluga wylogowania
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginManager.getInstance().logOut();
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

    public void mButtonChangePswOnClick(View v) {
        //obsluga zmiany hasla
        Intent myIntent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(myIntent);
    }

    public void mButtonDeleteOnClick(View v) {
        //obsluga usuniecia konta
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm your password to delete account")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        password = input.getText().toString();
                        manageHttpConnection();
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

    private void manageHttpConnection() {
        String url = httpRequests.deleteAccount(password);
        spinner.setVisibility(View.VISIBLE);
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse() {
            @Override
            public void processFinish(String result, Boolean timeout) {
                if (timeout) {
                    showAlert("Error with server connection. Try again later.");
                } else {
                    if (result.equals("true")) {
                        successfulChange("Account deleted successfully!");
                    } else showAlert("This is not your password!");
                }
                spinner.setVisibility(View.GONE);
            }
        }, new DownloadUrlWithoutJSONBody(getApplicationContext(), "DELETE")).execute(dataTransfer);
    }

    private String getUrl3() {
        StringBuilder googlePlaceUrl = new StringBuilder(getResources().getString(R.string.databaseIP));
        googlePlaceUrl.append("/api/users/getUsername");
        Log.d("created url", googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void successfulChange(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionController.logoutUser();
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        sessionController.logoutUser();
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
