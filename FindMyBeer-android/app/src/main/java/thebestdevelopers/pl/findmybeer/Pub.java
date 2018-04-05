package thebestdevelopers.pl.findmybeer;

public class Pub {
    String pubName;
    Double distance;
    int freeTablesCount;
    Double stars;

    public Pub(String _pubName, Double _distance, int _freeTableCount, Double _stars) {
        this.pubName = _pubName;
        this.distance = _distance;
        this.freeTablesCount = _freeTableCount;
        this.stars = _stars;
    }

}
