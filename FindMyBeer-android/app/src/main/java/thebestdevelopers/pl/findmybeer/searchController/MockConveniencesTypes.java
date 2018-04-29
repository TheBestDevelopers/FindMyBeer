package thebestdevelopers.pl.findmybeer.searchController;


import java.util.ArrayList;


public class MockConveniencesTypes {
    ArrayList<String> conveniences;

    public MockConveniencesTypes() {
        conveniences = new ArrayList<>();
        conveniences.add("smoking room");
        conveniences.add("tv");
        conveniences.add("board games");
        conveniences.add("computer games");
    }

    public ArrayList<String> getConveniences() {
        return conveniences;
    }
}
