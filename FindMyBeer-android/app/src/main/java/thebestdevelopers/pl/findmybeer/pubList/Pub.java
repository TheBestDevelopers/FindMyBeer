package thebestdevelopers.pl.findmybeer.pubList;

import android.util.Log;

import thebestdevelopers.pl.findmybeer.pubInfo.GetJsonResult;

public class Pub {
    String pubName;
    Integer distance = 0;
    Double longitude, latitude;
    Integer freeTablesCount;
    Double stars;
    String placeID;


    public Pub(String _pubName, Double _latitude, Double _longitude, Integer _freeTableCount, Double _stars, String _placeID) {
        this.pubName = _pubName;
        this.freeTablesCount = _freeTableCount;
        this.stars = _stars;
        this.longitude = _longitude;
        this.latitude = _latitude;
        this.placeID = _placeID;
    }

    public void setDistance(int _distance) {
        this.distance = _distance;
    }

    public String getPubName() {
        return pubName;
    }

    public Integer getDistance() {
        return distance;
    }

    public Double getStars() {
        return stars;
    }

    public Integer getFreeTablesCount() {
        return freeTablesCount;
    }

    public Double getLongitude() { return longitude; }

    public Double getLatitude() { return latitude; }

    public String getPlaceID() { return placeID; }

}
