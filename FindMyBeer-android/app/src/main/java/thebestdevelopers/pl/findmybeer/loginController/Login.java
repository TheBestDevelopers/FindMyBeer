package thebestdevelopers.pl.findmybeer.loginController;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlForAuthentication;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.Register;
import thebestdevelopers.pl.findmybeer.favController.PubData;
import thebestdevelopers.pl.findmybeer.loginController.LoginParser;
import thebestdevelopers.pl.findmybeer.loginController.Role;
import thebestdevelopers.pl.findmybeer.loginController.User;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;

public class Login extends AppCompatActivity {

    private static final String EMAIL = "email";
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private ProgressBar spinner;
    private HttpRequests httpRequests;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.mProgressBarLogin);
        spinner.setVisibility(View.GONE);

        mEditTextUsername = findViewById(R.id.mEditTextLogin);
        mEditTextPassword = findViewById(R.id.mEditTextPassword);

        httpRequests = new HttpRequests(this);

        facebookLoginButton = findViewById(R.id.mButtonLogInFacebook);
        facebookLoginButton.setBackgroundResource(R.drawable.roundedfacebookbutton);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", EMAIL));
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LOGIN_SUCCESS", "Success");
                facebookLoginButton.setVisibility(View.INVISIBLE); //<- IMPORTANT
                Intent intent = new Intent(getBaseContext(), HomeTab.class);
                startActivity(intent);
                finish();//<- IMPORTANT
            }

            @Override
            public void onCancel() {
                Log.d("LOGIN_CANCEL", "Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("LOGIN_ERROR", "Error");
            }
        });
    }

    public void mButtonLogInOnClick(View v){
        spinner.setVisibility(View.VISIBLE);
//        Intent myIntent = new Intent(getBaseContext(), HomeTab.class);
//        startActivity(myIntent);
        manageHttpConnection();
    }

    public void mButtonRegisterOnClick(View v){
        Intent myIntent = new Intent(getBaseContext(), Register.class);
        startActivity(myIntent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data);
    }

    private void manageHttpConnection() {
        String url = httpRequests.authUser();
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        ArrayList<String> cookies = new ArrayList<>();
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse(){
            @Override
            public void processFinish(String result, Boolean timeout){
                if (timeout) {
                    showAlert("Cannot connect to database. Try again later.");
                }
                else {
                    if (result != null && result.length() != 0) {
                        LoginParser parser = new LoginParser();
                        User user = parser.parse(result);
                        if (user != null)
                        showAlert("Yuhuu. logged in!");
                        if (user.getRole() == Role.CLIENT) {
                            Intent myIntent = new Intent(getBaseContext(), HomeTab.class);
                            startActivity(myIntent);
                            finish();
                        }
                        else if (user.getRole() == Role.PUB) {
                            Intent myIntent = new Intent(getBaseContext(), PubDetails.class);
                            startActivity(myIntent);
                            finish();
                        }
                    } else {
                        showAlert("Such user doesn't exist!");
                    }
                }
                spinner.setVisibility(View.GONE);

            }
        }, new DownloadUrlForAuthentication(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString())).execute(dataTransfer);
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
