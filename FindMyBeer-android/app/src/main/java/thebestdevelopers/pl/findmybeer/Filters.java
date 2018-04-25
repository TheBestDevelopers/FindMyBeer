package thebestdevelopers.pl.findmybeer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Filters extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapterSortingTypes;
    ListView mListViewSorting;
    String checkedSortingType;
    ArrayList<String> sortingTypes = new ArrayList<>();

    ArrayAdapter<String> arrayAdapterConveniences;
    ListView mListViewConveniences;
    ArrayList<String> conveniences = new ArrayList<>();
    ArrayList<String> checkedConveniences = new ArrayList<>();
    ArrayList<Integer> checkedConveniencesPositions = new ArrayList<>();
    int checkedSortingTypePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //if (savedInstanceState != null) {
//        if (checkedSortingTypePosition != -1)
//            mListViewSorting.setItemChecked(checkedSortingTypePosition, true);
//        //}
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
        mListViewSorting.setItemChecked(0, true);


        conveniences.add("smoking room");
        conveniences.add("tv");
        conveniences.add("board games");
        conveniences.add("computer games");
        arrayAdapterConveniences = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, conveniences);
        mListViewConveniences = findViewById(R.id.mListViewConveniences);
        mListViewConveniences.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListViewConveniences.setAdapter(arrayAdapterConveniences);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("checkedSortingType", checkedSortingTypePosition);
        savedInstanceState.putIntegerArrayList("checkedConveniences", checkedConveniencesPositions);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mListViewSorting.setItemChecked(savedInstanceState.getInt("checkedSortingType"), true);
    }

    public void mButtonCancelOnClick(View v) {
        finish();
    }

    public void mButtonSaveOnClick(View v) {
        if (mListViewSorting.getCheckedItemPosition() == AdapterView.INVALID_POSITION)
            checkedSortingType = "";
        else {
            checkedSortingTypePosition = mListViewSorting.getCheckedItemPosition();
            checkedSortingType = sortingTypes.get(checkedSortingTypePosition);
        }
        checkedConveniences = getCheckedConveniences();
        Intent output = new Intent();
        output.putExtra("sorting type", checkedSortingType);
        output.putExtra("conveniences", checkedConveniences);
        //conveniences.get(mListViewConveniences)
        setResult(RESULT_OK, output);
        finish();
    }

    private ArrayList<String> getCheckedConveniences() {
        SparseBooleanArray checked = mListViewConveniences.getCheckedItemPositions();
        ArrayList<String> chosenConv = new ArrayList<>();
        for (int i = 0; i < mListViewConveniences.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                checkedConveniencesPositions.add(i);
                chosenConv.add(mListViewConveniences.getAdapter().getItem(i).toString());
            }
        }
        return chosenConv;
    }
}
