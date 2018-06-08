package thebestdevelopers.pl.findmybeer.profileController;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import thebestdevelopers.pl.findmybeer.R;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_change_password);
    }
}
