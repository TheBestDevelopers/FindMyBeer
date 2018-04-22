package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Filters extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ListView mListViewSorting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ArrayList<String> sortingTypes = new ArrayList<>();
        sortingTypes.add("distance ascending");
        sortingTypes.add("distance descending");
        sortingTypes.add("name ascending");
        sortingTypes.add("name descending");
        sortingTypes.add("rate ascending");
        sortingTypes.add("rate descending");
        sortingTypes.add("free tables ascending");
        sortingTypes.add("free tables descending");
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,sortingTypes);
        mListViewSorting = findViewById(R.id.mListViewSorting);
        mListViewSorting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListViewSorting.setAdapter(arrayAdapter);
    }


    public void mButtonCancelOnClick(View v) {
        finish();
    }

    public void mButtonSaveOnClick(View v) {
        Intent output = new Intent();
        output.putExtra("test", "test passed");
        setResult(RESULT_OK, output);
        finish();
    }
}
