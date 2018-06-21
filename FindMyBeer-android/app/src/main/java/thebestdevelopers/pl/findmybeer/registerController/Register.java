package thebestdevelopers.pl.findmybeer.registerController;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlRegister;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.loginController.Login;

public class Register extends AppCompatActivity {
    private HttpRequests httpRequests;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private ProgressBar spinner;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);
        httpRequests = new HttpRequests(this);
        mEditTextUsername = findViewById(R.id.mEditTextOldPswd);
        mEditTextPassword = findViewById(R.id.mEditTextNewPswd);
        mSignUpButton = findViewById(R.id.mButtonRegister);
        spinner = findViewById(R.id.mProgressBarLogin);
        spinner.setVisibility(View.GONE);
    }

    public void mButtonRegisterOnClick(View v){
        manageHttpConnection();
    }

    private void manageHttpConnection() {
        String url = httpRequests.authUser();
        spinner.setVisibility(View.VISIBLE);
        mSignUpButton.setEnabled(false);
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        final Context context = getApplicationContext();
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse(){
            @Override
            public void processFinish(String result, Boolean timeout){
                if (timeout) {
                    showAlert("\"Error with server connection. Try again later.");
                }
                else {
                    if (result != null) {
                        showAlert("You signed up successfully!");
                        Intent myIntent = new Intent(getBaseContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    } else {
                        showAlert("Something went wrong.");
                    }
                }
                spinner.setVisibility(View.GONE);
                mSignUpButton.setEnabled(true);
            }
        }, new DownloadUrlRegister(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString())).execute(dataTransfer);
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
