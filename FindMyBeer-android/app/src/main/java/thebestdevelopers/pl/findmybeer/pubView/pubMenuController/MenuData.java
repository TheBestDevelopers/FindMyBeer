package thebestdevelopers.pl.findmybeer.pubView.pubMenuController;

public class MenuData {

    private String mName, mType;


    public MenuData(String name, String type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public String getAdress() {
        return mType;
    }
}
