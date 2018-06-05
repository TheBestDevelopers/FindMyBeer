package thebestdevelopers.pl.findmybeer.pubList;

public class Pub {
    String pubName;
    Integer distance = 0;
    Double longitude, latitude;
    Integer freeTablesCount;
    Float rating;
    String placeID;


    public Pub(String _pubName, Double _latitude, Double _longitude, Integer _freeTableCount, Float _stars, String _placeID) {
        this.pubName = _pubName;
        this.freeTablesCount = _freeTableCount;
        this.rating = _stars;
        this.longitude = _longitude;
        this.latitude = _latitude;
        this.placeID = _placeID;
    }

    public Pub(String _placeID, Integer _freeTablesCount) {
        this.placeID = _placeID;
        this.freeTablesCount = _freeTablesCount;
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

    public Float getRating() {
        return rating;
    }

    public Integer getFreeTablesCount() {
        return freeTablesCount;
    }

    public Double getLongitude() { return longitude; }

    public Double getLatitude() { return latitude; }

    public String getPlaceID() { return placeID; }

}
