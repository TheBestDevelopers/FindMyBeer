package thebestdevelopers.pl.findmybeer.searchController;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;


import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.R;

public class Filters extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

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

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mIdTextView;
    private TextView mPhoneTextView;
    private TextView mWebTextView;
    private TextView mAttTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    String placeId = "";
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mGoogleApiClient = new GoogleApiClient.Builder(Filters.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mNameTextView = (TextView) findViewById(R.id.name);
        mAddressTextView = (TextView) findViewById(R.id.address);
        mIdTextView = (TextView) findViewById(R.id.place_id);
        mPhoneTextView = (TextView) findViewById(R.id.phone);
        mWebTextView = (TextView) findViewById(R.id.web);
        mAttTextView = (TextView) findViewById(R.id.att);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

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

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            LatLng placeLatLong = place.getLatLng();
            latitude = placeLatLong.latitude;
            longitude = placeLatLong.longitude;;
            mNameTextView.setText(Html.fromHtml(place.getName() + ""));
            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
            mWebTextView.setText(place.getWebsiteUri() + "");
            if (attributions != null) {
                mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
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
        output.putExtra("latitude", latitude);
        output.putExtra("longitude", longitude);
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
