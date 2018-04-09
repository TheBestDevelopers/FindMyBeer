package thebestdevelopers.pl.findmybeer.pubList;

import android.support.annotation.NonNull;

public class Pub implements Comparable<Pub>{
    String pubName;
    Integer distance=0;
    Double longitude, latitude;
    Integer freeTablesCount;
    Double stars;

    public Pub(String _pubName, Integer _distance, Integer _freeTableCount, Double _stars) {
        this.pubName = _pubName;
        this.distance = _distance;
        this.freeTablesCount = _freeTableCount;
        this.stars = _stars;
    }

    public Pub(String _pubName, Double _latitude, Double _longitude, Integer _freeTableCount, Double _stars) {
        this.pubName = _pubName;
        this.freeTablesCount = _freeTableCount;
        this.stars = _stars;
        this.longitude = _longitude;
        this.latitude = _latitude;
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


    @Override
    public int compareTo(@NonNull Pub other) {
        return distance.compareTo(other.distance);
    }
}
