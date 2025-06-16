package strategy;

import model.Location;

public interface DistanceCalculator {
    double distanceKm(Location a, Location b);
}
