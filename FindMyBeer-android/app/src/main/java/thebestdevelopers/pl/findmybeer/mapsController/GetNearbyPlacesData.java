package thebestdevelopers.pl.findmybeer.mapsController;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String>{

    String googlePlaces, url;
    GoogleMap mGoogleMap;

    @Override
    protected String doInBackground(Object... objects) {
        mGoogleMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaces = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaces;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> list = null;
        DataParser parser = new DataParser();
        list = parser.parse(s);
        showNearbyPlaces(list);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> list) {
        for (int i = 0; i<list.size();i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> place = list.get(i);
            String name = place.get("place_name");
            String vicinity = place.get("vicinity");
            Double lat = Double.parseDouble(place.get("lat"));
            Double lng = Double.parseDouble(place.get("lng"));

            LatLng ll = new LatLng(lat, lng);
            markerOptions.position(ll);
            markerOptions.title(name+":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mGoogleMap.addMarker(markerOptions);
        }
    }
}
