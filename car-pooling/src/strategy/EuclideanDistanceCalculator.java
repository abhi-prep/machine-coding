package strategy;

import model.Location;

public class EuclideanDistanceCalculator implements DistanceCalculator {
    @Override
    public double distanceKm(Location a, Location b) {
        double dx = a.getLat() - b.getLat();
        double dy = a.getLng() - b.getLng();
        // 1 degree ≈ 111 km
        return Math.sqrt(dx * dx + dy * dy) * 111;
    }
}
