package thebestdevelopers.pl.findmybeer.pubView.pubProfileController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlWithoutJSONBody;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.SessionController;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword;
    EditText newPassword;
    SessionController sessionController;
    HttpRequests httpRequests;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_change_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mProgressBar = findViewById(R.id.mProgressBarPswd);
        mProgressBar.setVisibility(View.GONE);
        httpRequests = new HttpRequests(this);
        oldPassword = findViewById(R.id.mEditTextOldPswd);
        newPassword = findViewById(R.id.mEditTextNewPswd);
        sessionController = new SessionController(this);
    }

    public void onClick(View v) {
        manageHttpConnection();
    }

    private void manageHttpConnection() {
        String url = httpRequests.changePassword(oldPassword.getText().toString(), newPassword.getText().toString());
        mProgressBar.setVisibility(View.VISIBLE);
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse(){
            @Override
            public void processFinish(String result, Boolean timeout){
                if (timeout) {
                    showAlert("Error with server connection. Try again later.");
                }
                else {
                    if (result.equals("true")) {
                        successfulChange("Password changed successfully!");
                    }
                    else showAlert("This is not your password!");
                }
                mProgressBar.setVisibility(View.GONE);
            }
        }, new DownloadUrlWithoutJSONBody(getApplicationContext(), "PUT")).execute(dataTransfer);
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
                .setPositiveButton("OK, log out", new DialogInterface.OnClickListener() {
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

