package thebestdevelopers.pl.findmybeer.searchController;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.GetDataAsyncTask;
import thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks.IAsyncResponse;
import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.DownloadUrlWithoutJSONBody;
import thebestdevelopers.pl.findmybeer.ApiController.HttpRequests;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.searchController.Conveniences.ConveniencesParser;
import thebestdevelopers.pl.findmybeer.searchController.Sorting.SortingTypesStore;

//<<<<<<< HEAD
//=======
//>>>>>>> ae5319b5f38db02e4826da28b0b18a6b697d2511

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
    int checkedSortingTypePosition = 0;
    CheckBox mUseMyLocationCheckBox;
    private ProgressBar spinner;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;

    private TextView mNameTextView;
    private TextView mAddressTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(0, 0), new LatLng(0, 0));
    String placeId = "";
    Double latitude, longitude;
    HttpRequests httpRequests;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonSave = findViewById(R.id.mButtonSave);
        buttonSave.setEnabled(false);
        spinner = findViewById(R.id.mProgressBarFilters);
        spinner.setVisibility(View.VISIBLE);

        httpRequests = new HttpRequests(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mUseMyLocationCheckBox = findViewById(R.id.mUseMyLocationCheckBox);
        mUseMyLocationCheckBox.setChecked(true);
        setAAutoCompleteAdapter();
        setSortingTypeChooser();
        getConveniencesListFromApi();

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
                return;
            }
            final Place place = places.get(0);
            LatLng placeLatLong = place.getLatLng();
            latitude = placeLatLong.latitude;
            longitude = placeLatLong.longitude;
            mNameTextView.setText((place.getName()));
            mAddressTextView.setText((place.getAddress()));
            mUseMyLocationCheckBox.setChecked(false);
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
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
        if (mUseMyLocationCheckBox.isChecked()) {
            output.putExtra("latitude", 0);
            output.putExtra("longitude", 0);
        } else {
            output.putExtra("latitude", latitude);
            output.putExtra("longitude", longitude);
        }
        setResult(RESULT_OK, output);
        finish();
    }

    private void setAAutoCompleteAdapter() {
        mGoogleApiClient = new GoogleApiClient.Builder(Filters.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mNameTextView = findViewById(R.id.name);
        mAddressTextView = findViewById(R.id.address);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
    }

    private void setSortingTypeChooser() {
        SortingTypesStore sortingTypesStore = new SortingTypesStore();
        sortingTypes = sortingTypesStore.getSortingTypes();

        arrayAdapterSortingTypes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, sortingTypes);
        mListViewSorting = findViewById(R.id.mListViewSorting);
        mListViewSorting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListViewSorting.setAdapter(arrayAdapterSortingTypes);
        mListViewSorting.setItemChecked(0, true);
    }

    private void setConveniencesChooser() {
        arrayAdapterConveniences = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, conveniences);
        mListViewConveniences = findViewById(R.id.mListViewConveniences);
        mListViewConveniences.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListViewConveniences.setAdapter(arrayAdapterConveniences);
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

    private void getConveniencesListFromApi() {
        String url = httpRequests.getConveniencesUrl();
        Object dataTransfer[] = new Object[1];
        dataTransfer[0] = url;
        GetDataAsyncTask asyncTask = (GetDataAsyncTask) new GetDataAsyncTask(new IAsyncResponse() {
            @Override
            public void processFinish(String result, Boolean timeout) {
                if (timeout) {
                    showAlert("Cannot connect to database. Try again later.");
                } else {
                    ConveniencesParser parser = new ConveniencesParser();
                    conveniences = parser.parse(result);
                    if (conveniences != null && conveniences.size() != 0) {
                        // spinner.setVisibility(View.GONE);
                        for (String s : conveniences)
                            s.replace("_", " ");
                        setConveniencesChooser();
                    } else {
                        showAlert("There are no places nearby!");
                    }
                }
                spinner.setVisibility(View.GONE);
                buttonSave.setEnabled(true);
            }
        }, new DownloadUrlWithoutJSONBody(getApplicationContext(), "GET")).execute(dataTransfer);
    }

    private void showAlert(String message) {
        //temporary solution - should appear a message box or a textview with this info and it should appear once...
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
