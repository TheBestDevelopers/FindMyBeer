package thebestdevelopers.pl.findmybeer.favController;

public class PubData {

    private String mName, mAddress, mID;


    public PubData(String id, String name, String address) {
        mName = name;
        mAddress = address;
        mID = id;
    }

    public String getName() {
        return mName;
    }

    public String getAdress() {
        return mAddress;
    }

    public String getId() { return mID; }

}
