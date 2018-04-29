package thebestdevelopers.pl.findmybeer;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class MockPubsData {
    ArrayList<Pub> pubs;

    public ArrayList<Pub> initializePubs() {
        pubs = new ArrayList<>();
        pubs.add(new Pub("trzy siostry", 50.2591173, 19.0266095, 5, 5.0, "ChIJc-kYGzbOFkcRC563sBLpD6w"));
        pubs.add(new Pub("dubai food", 50.2698693,19.0261198, 4, 4.5, "ChIJJ3UMCiXOFkcRzO760q6SSo0"));
        pubs.add(new Pub("Klubowa", 50.2573933, 19.0229366, 3, 5.0, "ChIJge1n50nOFkcR0N6ku8YtrOQ"));
        return pubs;
    }

}
