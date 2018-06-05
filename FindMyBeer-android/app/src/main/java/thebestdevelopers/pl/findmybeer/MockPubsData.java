package thebestdevelopers.pl.findmybeer;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.pubList.Pub;

public class MockPubsData {
    private ArrayList<Pub> pubs;

//    public ArrayList<Pub> initializePubs() {
//        pubs = new ArrayList<>();
//        pubs.add(new Pub("trzy siostry", 50.2591173, 19.0266095, 5, 5.0f, "ChIJc-kYGzbOFkcRC563sBLpD6w"));
//        pubs.add(new Pub("dubai food", 50.2698693,19.0261198, 4, 4.5f, "ChIJJ3UMCiXOFkcRzO760q6SSo0"));
//        pubs.add(new Pub("Klubowa", 50.2573933, 19.0229366, 3, 5.0f, "ChIJge1n50nOFkcR0N6ku8YtrOQ"));
//        return pubs;
//    }

    public MockPubsData() {
        pubs = new ArrayList<>();
        pubs.add(new Pub("ChIJzbuF4UnOFkcRKElcz9cDnvE", 2));
        pubs.add(new Pub("ChIJrZ_K30nOFkcRHJwbFU5K5mk", 4));
        pubs.add(new Pub("ChIJge1n50nOFkcR0N6ku8YtrOQ", 3));
        pubs.add(new Pub("ChIJvbH7c0fOFkcR_imJOWYF-HE", 3));
        pubs.add(new Pub("ChIJHTRK_knOFkcRsyFQYBa9eOk", 1));
        pubs.add(new Pub("ChIJwVHwfTXOFkcRP26yFKJhn5I", 0));
    }

//    public ArrayList<Pub> initializePubs() {
//        pubs = new ArrayList<>();
//        pubs.add(new Pub("ChIJzbuF4UnOFkcRKElcz9cDnvE", 2));
//        pubs.add(new Pub("ChIJrZ_K30nOFkcRHJwbFU5K5mk", 4));
//        pubs.add(new Pub("ChIJge1n50nOFkcR0N6ku8YtrOQ", 3));
//        pubs.add(new Pub("ChIJvbH7c0fOFkcR_imJOWYF-HE", 3));
//        pubs.add(new Pub("ChIJHTRK_knOFkcRsyFQYBa9eOk", 1));
//        pubs.add(new Pub("ChIJwVHwfTXOFkcRP26yFKJhn5I", 0));
//        return pubs;
//    }

    public ArrayList<Pub> getPubs() {
        return pubs;
    }
    public Integer getFreeTablesCount(String pubId) {
        for (Pub pub : pubs) {
            if (pub.getPlaceID().equals(pubId)) {
                return pub.getFreeTablesCount();
            }
        }
        return 0;
    }


}
