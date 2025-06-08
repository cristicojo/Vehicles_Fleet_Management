package vehiclesfleetmanagement.sort;

import vehiclesfleetmanagement.entity.Vehicle;

import java.util.Comparator;

public class SortByRcaPriority implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle a, Vehicle b) {
        return a.getRcaPriority() - b.getRcaPriority();
    }
}
