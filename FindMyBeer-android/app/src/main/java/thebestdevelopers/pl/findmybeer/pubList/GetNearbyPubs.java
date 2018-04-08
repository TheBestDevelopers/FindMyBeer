package thebestdevelopers.pl.findmybeer.pubList;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import thebestdevelopers.pl.findmybeer.HomeTab;
import thebestdevelopers.pl.findmybeer.mapsController.DataParser;
import thebestdevelopers.pl.findmybeer.mapsController.DownloadUrl;
import thebestdevelopers.pl.findmybeer.pubInfo.DataPubParser;
import thebestdevelopers.pl.findmybeer.pubInfo.DownloadPubUrl;
import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class GetNearbyPubs extends AsyncTask<Object, String, String> {

        private String googlePlacesData;
        String url;
        String placeName, vicinity, rating;
        List<HashMap<String, String>> nearbyPlaceList;
        ArrayList<Pub> pubs;

        public GetNearbyPubs(ArrayList<Pub> _pubs) {
               this.pubs = _pubs;
        }

        @Override
        protected String doInBackground(Object... objects) {

                url = (String)objects[0];
                DownloadUrl downloadUrl = new DownloadUrl();
                try {
                        googlePlacesData = downloadUrl.readUrl(url);
                } catch (IOException e) {
                         e.printStackTrace();
                }

                return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String s) {
                Random randGen = new Random();
                DataParser parser = new DataParser();
                nearbyPlaceList = parser.parse(s);
                Log.d("placedata", "PARSE METHOD IN PUB LIST");
                for (HashMap<String, String> place : nearbyPlaceList ) {
                        showNearbyPlaces(place);
                        pubs.add(new Pub(placeName, vicinity, randGen.nextInt()*10, Double.parseDouble(rating)));
                }

        }

        private void showNearbyPlaces(HashMap<String, String> googlePlace)
        {
                placeName = googlePlace.get("place_name");
                vicinity = googlePlace.get("vicinity");
                rating = googlePlace.get("rating");

        }

        public List<HashMap<String, String>> getNearbyPlaceList() {
                return nearbyPlaceList;

        }

}