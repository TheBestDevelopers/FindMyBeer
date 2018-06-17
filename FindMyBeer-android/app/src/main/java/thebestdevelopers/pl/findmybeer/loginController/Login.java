package thebestdevelopers.pl.findmybeer.loginController;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlForAuthentication;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.SessionController;
import thebestdevelopers.pl.findmybeer.registerController.Register;
import thebestdevelopers.pl.findmybeer.pubView.pubDetailsController.PubDetails;

public class Login extends AppCompatActivity {

    private static final String EMAIL = "email";
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private ProgressBar spinner;
    private HttpRequests httpRequests;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mLoginButton;
    private Button mSignUpButton;
    private Button mFacebookLoginButton;
    private SessionController sessionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.mProgressBarLogin);
        spinner.setVisibility(View.GONE);
        mLoginButton = findViewById(R.id.mButtonLogIn);
        mSignUpButton = findViewById(R.id.mButtonRegister);
        mFacebookLoginButton = findViewById(R.id.mButtonLogInFacebook);

        mEditTextUsername = findViewById(R.id.mEditTextLogin);
        mEditTextPassword = findViewById(R.id.mEditTextPassword);

        sessionController = new SessionController(getApplicationContext());
        httpRequests = new HttpRequests(this);
        facebookLoginButton = findViewById(R.id.mButtonLogInFacebook);
        facebookLoginButton.setBackgroundResource(R.drawable.roundedfacebookbutton);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", EMAIL));
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLoginButton.setVisibility(View.INVISIBLE); //<- IMPORTANT
                sessionController.createLoginSession(""); //
                Intent intent = new Intent(getBaseContext(), HomeTab.class);
                startActivity(intent);
                finish();//<- IMPORTANT
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    public void mButtonLogInOnClick(View v){

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
      //  callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data);
    }

    private void manageHttpConnection() {
        String url = httpRequests.authUser();
        spinner.setVisibility(View.VISIBLE);
        mLoginButton.setEnabled(false);
        mSignUpButton.setEnabled(false);
        mFacebookLoginButton.setEnabled(false);
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse(){
            @Override
            public void processFinish(String result, Boolean timeout){
                if (timeout) {
                    showAlert("Error with server connection. Try again later.");
                }
                else {
                    if (result != null && result.length() != 0) {
                        LoginParser parser = new LoginParser();
                        User user = parser.parse(result);
                        if (user != null) {

                            if (user.getRole() == Role.CLIENT) {
                                Intent myIntent = new Intent(getBaseContext(), HomeTab.class);
                                startActivity(myIntent);
                                finish();
                            } else if (user.getRole() == Role.PUB) {
                                Intent myIntent = new Intent(getBaseContext(), PubDetails.class);
                                startActivity(myIntent);
                                finish();
                            }
                        }
                    } else {
                        showAlert("Improper username or password.");
                    }
                }
                spinner.setVisibility(View.GONE);
                mLoginButton.setEnabled(true);
                mSignUpButton.setEnabled(true);
                mFacebookLoginButton.setEnabled(true);

            }
        }, new DownloadUrlForAuthentication(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString(), getApplicationContext())).execute(dataTransfer);
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
