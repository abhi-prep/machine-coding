package factory;

import model.Location;
import model.Ride;

public class RideFactory {
    public static void createRide(String rideId, double sLat, double sLng,
                                  double eLat, double eLng, String driverName, int seats) {
        Location start = new Location(sLat, sLng);
        Location end = new Location(eLat, eLng);
//        return new Ride( start, end, driverName, seats);
    }
}
