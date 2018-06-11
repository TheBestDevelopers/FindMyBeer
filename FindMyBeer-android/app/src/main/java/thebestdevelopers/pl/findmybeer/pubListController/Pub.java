package thebestdevelopers.pl.findmybeer.pubListController;

public class Pub {
    String pubName;
    Integer distance = 0;
    Integer freeTablesCount;
    Double rating;
    Integer id;

    public Pub(String _pubName, Integer _distance, Integer _freeTableCount, Double _stars, Integer _id) {
        this.pubName = _pubName;
        this.freeTablesCount = _freeTableCount;
        this.rating = _stars;
        this.distance = _distance;
        this.id = _id;
    }

    public Pub(Integer _id, Integer _freeTablesCount) {
        this.id = _id;
        this.freeTablesCount = _freeTablesCount;
    }

    public String getPubName() {
        return pubName;
    }

    public Integer getDistance() {
        return distance;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getFreeTablesCount() {
        return freeTablesCount;
    }

    public Integer getPlaceID() { return id; }

}
