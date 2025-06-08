package vehiclesfleetmanagement.sort;

import vehiclesfleetmanagement.entity.Vehicle;

import java.util.Comparator;

public class SortByRovinietaPriority implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle a, Vehicle b) {
        return a.getRovinietaPriority() - b.getRovinietaPriority();
    }
}
