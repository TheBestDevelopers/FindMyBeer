package thebestdevelopers.pl.findmybeer.searchController;

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

import thebestdevelopers.pl.findmybeer.R;

public class Filters extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapterSortingTypes;
    ListView mListViewSorting;
    String checkedSortingType;
    ArrayList<String> sortingTypes;

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

        SortingTypesStore sortingTypesStore = new SortingTypesStore();
        sortingTypes = sortingTypesStore.getSortingTypes();
        arrayAdapterSortingTypes = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice, sortingTypes);
        mListViewSorting = findViewById(R.id.mListViewSorting);
        mListViewSorting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListViewSorting.setAdapter(arrayAdapterSortingTypes);
        mListViewSorting.setItemChecked(0, true);

        MockConveniencesTypes mockConveniencesTypes = new MockConveniencesTypes();
        conveniences = mockConveniencesTypes.getConveniences();
        arrayAdapterConveniences = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice, conveniences);
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
        getCheckedSortingType();
        getCheckedConveniences();
        Intent output = new Intent();
        output.putExtra("sorting type", checkedSortingType);
        output.putExtra("conveniences", checkedConveniences);
        setResult(RESULT_OK, output);
        finish();
    }

    private void getCheckedSortingType() {
        if (mListViewSorting.getCheckedItemPosition() == AdapterView.INVALID_POSITION)
            checkedSortingType = "";
        else {
            checkedSortingTypePosition = mListViewSorting.getCheckedItemPosition();
            checkedSortingType = sortingTypes.get(checkedSortingTypePosition);
        }
    }

    private void getCheckedConveniences() {
        SparseBooleanArray checked = mListViewConveniences.getCheckedItemPositions();
        for (int i = 0; i < mListViewConveniences.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                checkedConveniencesPositions.add(i);
                checkedConveniences.add(mListViewConveniences.getAdapter().getItem(i).toString());
            }
        }
    }
}
