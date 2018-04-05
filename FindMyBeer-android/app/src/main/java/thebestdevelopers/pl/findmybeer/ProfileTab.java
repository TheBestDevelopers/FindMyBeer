package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import thebestdevelopers.pl.findmybeer.mapsController.MapTab;

public class ProfileTab extends AppCompatActivity {

    TextView mLoginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_profile_tab);
        overridePendingTransition(0, 0);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs5);
        BottomNavigationViewHelper.disableShiftMode(tabs);
        tabs.getMenu().findItem(R.id.action_user).setChecked(true);
        Intent i;

        mLoginText = (TextView)findViewById(R.id.tLogin);
        mLoginText.setText("Imie Nazwisko");

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
                                break;
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                            case R.id.action_search:
                                i = new Intent(getApplicationContext(), SearchTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                            case R.id.action_map:
                                i = new Intent(getApplicationContext(), MapTab.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                                break;
                        }

                        return true;
                    }
                });
    }

    public void mButtonLogOutOnClick(View v){
        //obsluga wylogowania

        Intent myIntent = new Intent(getApplicationContext(), Login.class);
        startActivity(myIntent);
    }

    public void mButtonChangePswOnClick(View v){
        //obsluga zmiany hasla

        Intent myIntent = new Intent(getApplicationContext(), HomeTab.class);
        startActivity(myIntent);
    }

    public void mButtonDeleteOnClick(View v){
        //obsluga usuniecia konta

        Intent myIntent = new Intent(getApplicationContext(), Login.class);
        startActivity(myIntent);
    }
}
