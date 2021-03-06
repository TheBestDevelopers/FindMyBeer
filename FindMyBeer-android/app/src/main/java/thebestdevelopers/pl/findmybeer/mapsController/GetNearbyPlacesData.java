package thebestdevelopers.pl.findmybeer.mapsController;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    String url;
    List<HashMap<String, String>> nearbyPlaceList;
    Activity act;
    private Context context;

    public GetNearbyPlacesData(Context _context) {
        context = _context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        act = (Activity) objects[2];
        DownloadUrl downloadUrl = new DownloadUrl(context);
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            return "Exception";
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        if (!equals("Exception")) {
            try {
                DataParser parser = new DataParser();
                nearbyPlaceList = parser.parse(s);
                showNearbyPlaces();
            } catch (Exception e) {
                Toast.makeText(act, "There's no server connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(act, "There's no server connection", Toast.LENGTH_LONG).show();
        }
    }

    private void showNearbyPlaces() {
        for (int i = 0; i < nearbyPlaceList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            boolean ourPub = false;
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String id = googlePlace.get("place_id");
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName);
            if (googlePlace.get("our_pub").isEmpty())
                markerOptions.snippet(id);
            else {
                markerOptions.snippet("!!!" + id);
                ourPub = true;
            }
            if (ourPub)
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            else
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }

}