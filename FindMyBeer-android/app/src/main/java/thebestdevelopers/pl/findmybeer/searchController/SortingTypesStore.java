package thebestdevelopers.pl.findmybeer.searchController;


import java.util.ArrayList;

public class SortingTypesStore {
    ArrayList<String> sortingTypes;

    public SortingTypesStore() {
        sortingTypes = new ArrayList<>();
        sortingTypes.add("distance ascending");
        sortingTypes.add("distance descending");
        sortingTypes.add("name ascending");
        sortingTypes.add("name descending");
        sortingTypes.add("rate ascending");
        sortingTypes.add("rate descending");
        sortingTypes.add("free tables ascending");
        sortingTypes.add("free tables descending");
    }

    public ArrayList<String> getSortingTypes() {
        return sortingTypes;
    }

}
