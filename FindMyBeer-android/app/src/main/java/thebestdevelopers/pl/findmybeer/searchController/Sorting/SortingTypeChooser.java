package thebestdevelopers.pl.findmybeer.searchController.Sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import thebestdevelopers.pl.findmybeer.pubListController.Pub;


public class SortingTypeChooser {

    ArrayList<Pub> pubListToSort;

    public SortingTypeChooser(ArrayList<Pub> _listToSort) {
        this.pubListToSort = _listToSort;
    }

    public  SortingTypeChooser() {}

    public void setListToSort(ArrayList<Pub> _listToSort) {
        this.pubListToSort = _listToSort;
    }

    public ArrayList<Pub> getSortedList(String sortingType)
    {
        switch (sortingType) {
            case "distance ascending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p1.getDistance() - p2.getDistance();
                    }
                });
                break;
            case "distance descending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p2.getDistance() - p1.getDistance();
                    }
                });
                break;
            case "name ascending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p1.getPubName().toLowerCase().compareTo(p2.getPubName().toLowerCase());
                    }
                });
                break;
            case "name descending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p2.getPubName().toLowerCase().compareTo(p1.getPubName().toLowerCase());
                    }
                });
                break;
            case "rate ascending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p1.getRating().compareTo(p2.getRating());
                    }
                });
                break;
            case "rate descending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return  p2.getRating().compareTo(p1.getRating());
                    }
                });
                break;
            case "free tables ascending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p1.getFreeTablesCount() - p2.getFreeTablesCount();
                    }
                });
                break;
            case "free tables descending" :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p2.getFreeTablesCount() - p1.getFreeTablesCount();
                    }
                });
                break;
            default :
                Collections.sort(pubListToSort, new Comparator<Pub>() {
                    @Override
                    public int compare(Pub p1, Pub p2) {
                        return p1.getDistance() - p2.getDistance();
                    }
                });
        }

        return pubListToSort;
    }
}
