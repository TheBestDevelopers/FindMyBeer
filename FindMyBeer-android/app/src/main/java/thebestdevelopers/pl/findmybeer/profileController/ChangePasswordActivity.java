package thebestdevelopers.pl.findmybeer.profileController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.BottomNavigationViewHelper;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.SessionController;
import thebestdevelopers.pl.findmybeer.loginController.Login;

public class ChangePasswordActivity extends AppCompatActivity {

    String id;
    EditText oldPassword;
    EditText newPassword;
    SessionController sessionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_change_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        oldPassword = findViewById(R.id.mEditTextOldPswd);
        newPassword = findViewById(R.id.mEditTextNewPswd);
        sessionController = new SessionController(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("placeID"); //wczytanie id miejsca oznaczonego markerem
        }
    }
    //url do zmiany hasla
    private String getUrl() {
        StringBuilder url = new StringBuilder(getResources().getString(R.string.databaseIP));
        url.append("/api/users/?password=");
        url.append(oldPassword.getText());
        url.append("&newPassword=");
        url.append(newPassword.getText());
        return url.toString();
    }

    public void onClick(View v) {

        Object dataTransfer[] = new Object[1];
        GetJsonResult getNearbyPlacesData = new GetJsonResult(this, getApplicationContext(), "PUT");
        String url = getUrl();
        dataTransfer[0] = url;
        try {
            getNearbyPlacesData.execute(dataTransfer);
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cannot connect to the server!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        showAlert("Password changed successfully!");
        sessionController.logoutUser();

    }
    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
