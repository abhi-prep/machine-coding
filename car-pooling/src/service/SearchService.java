package service;

import model.Location;
import model.Ride;
import repository.RideRepository;
import strategy.DistanceCalculator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SearchService {
    private final RideRepository rideRepo;
    private final DistanceCalculator distCalc;
    public SearchService(RideRepository rides, DistanceCalculator dc) {
        this.rideRepo = rides;
        this.distCalc = dc;
    }
    public List<Ride> findNearby(double lat, double lng, double radiusKm) {
        Location userStart = new Location(lat, lng);
        return rideRepo.findAll().stream()
                .filter(r -> distCalc.distanceKm(userStart, r.getStart()) <= radiusKm)
                .sorted(Comparator.comparingDouble(
                        r -> distCalc.distanceKm(userStart, r.getStart())))
                .collect(Collectors.toList());
    }
}