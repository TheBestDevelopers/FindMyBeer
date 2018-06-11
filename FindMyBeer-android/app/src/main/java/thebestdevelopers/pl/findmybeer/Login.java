package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private static final String EMAIL = "email";


    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
            setContentView(R.layout.activity_login);
            facebookLoginButton = (LoginButton) findViewById(R.id.mButtonLogInFacebook);
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
        Intent myIntent = new Intent(getBaseContext(), HomeTab.class);
        startActivity(myIntent);
        finish();
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

}
