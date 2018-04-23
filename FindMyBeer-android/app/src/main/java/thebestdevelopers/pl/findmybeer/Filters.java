package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Filters extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapterSortingTypes;
    ListView mListViewSorting;
    String checkedSortingType;
    ArrayList<String> sortingTypes = new ArrayList<>();

    ArrayAdapter<String> arrayAdapterConveniences;
    ListView mListViewConveniences;
    ArrayList<String> conveniences = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sortingTypes.add("distance ascending");
        sortingTypes.add("distance descending");
        sortingTypes.add("name ascending");
        sortingTypes.add("name descending");
        sortingTypes.add("rate ascending");
        sortingTypes.add("rate descending");
        sortingTypes.add("free tables ascending");
        sortingTypes.add("free tables descending");
        arrayAdapterSortingTypes = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,sortingTypes);
        mListViewSorting = findViewById(R.id.mListViewSorting);
        mListViewSorting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListViewSorting.setAdapter(arrayAdapterSortingTypes);


        conveniences.add("distance ascending");
        conveniences.add("distance descending");
        conveniences.add("name ascending");
        conveniences.add("name descending");
        conveniences.add("rate ascending");
        conveniences.add("rate descending");
        conveniences.add("free tables ascending");
        conveniences.add("free tables descending");
        arrayAdapterConveniences = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, conveniences);
        mListViewConveniences = findViewById(R.id.mListViewConveniences);
        mListViewConveniences.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListViewConveniences.setAdapter(arrayAdapterConveniences);
    }


    public void mButtonCancelOnClick(View v) {
        finish();
    }

    public void mButtonSaveOnClick(View v) {
        if (mListViewSorting.getCheckedItemPosition() == AdapterView.INVALID_POSITION)
            checkedSortingType = "";
        else checkedSortingType = sortingTypes.get(mListViewSorting.getCheckedItemPosition());
        ArrayList<String> chosenConveniences = getChosenConveniences();
        Intent output = new Intent();
        output.putExtra("sorting type", checkedSortingType);
        output.putExtra("conveniences", chosenConveniences);
        //conveniences.get(mListViewConveniences)
        setResult(RESULT_OK, output);
        finish();
    }

    private ArrayList<String> getChosenConveniences() {
        SparseBooleanArray checked = mListViewConveniences.getCheckedItemPositions();
        ArrayList<String> chosenConv = new ArrayList<>();
        for (int i = 0; i < mListViewConveniences.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                chosenConv.add(mListViewConveniences.getAdapter().getItem(i).toString());
            }
        }
        return chosenConv;
    }
}
