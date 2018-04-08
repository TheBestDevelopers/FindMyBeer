package thebestdevelopers.pl.findmybeer.pubList;

public class Pub {
    String pubName;
    Double distance;
    Integer freeTablesCount;
    Double stars;

    public Pub(String _pubName, Double _distance, Integer _freeTableCount, Double _stars) {
        this.pubName = _pubName;
        this.distance = _distance;
        this.freeTablesCount = _freeTableCount;
        this.stars = _stars;
    }

    public String getPubName() {
        return pubName;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getStars() {
        return stars;
    }

    public Integer getFreeTablesCount() {
        return freeTablesCount;
    }

}
