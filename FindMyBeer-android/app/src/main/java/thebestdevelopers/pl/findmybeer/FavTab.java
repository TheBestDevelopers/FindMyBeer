package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FavTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_tab);
        BottomNavigationView tabs = (BottomNavigationView) findViewById(R.id.navigationtabs2);
        BottomNavigationViewHelper.disableShiftMode(tabs);

        Intent i;
        tabs.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                i = new Intent(getApplicationContext(), HomeTab.class);
                                startActivity(i);
                                break;
                            case R.id.action_fav:
                                i = new Intent(getApplicationContext(), FavTab.class);
                                startActivity(i);
                                break;
                            case R.id.action_search:
                                i = new Intent(getApplicationContext(), SearchTab.class);
                                startActivity(i);
                                break;
                            case R.id.action_map:
                                i = new Intent(getApplicationContext(), MapTab.class);
                                startActivity(i);
                                break;
                            case R.id.action_user:
                                i = new Intent(getApplicationContext(), ProfileTab.class);
                                startActivity(i);
                                break;
                        }

                        return true;
                    }
                });
    }
}
