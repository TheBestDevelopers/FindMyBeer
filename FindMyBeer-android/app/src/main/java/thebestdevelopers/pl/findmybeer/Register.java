package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void mButtonRegisterOnClick(View v){
        Intent myIntent = new Intent(getBaseContext(), MainView.class);
        startActivity(myIntent);
    }
}