import model.Booking;
import model.Person;
import model.Ride;
import service.RideService;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        RideService rideService = new RideService();

        // register users
        Person alice = rideService.registerPerson("Alice");
        Person bob   = rideService.registerPerson("Bob");
        Person eve   = rideService.registerPerson("Eve");

        // Alice and Bob offer rides
        Ride r1 = rideService.offerRide(alice.getPersonId(), 12.96,77.59, 12.98,77.60, 3);
        Ride r2 = rideService.offerRide(bob.getPersonId(),   12.97,77.58, 12.99,77.61, 2);

        // Eve searches nearby rides
        List<Ride> nearbyRides = rideService.findNearbyRides(eve.getPersonId(), 12.965, 77.585);
        System.out.println("Nearby rides: " +
                nearbyRides.stream().map(Ride::getRideId).toList());

        // Eve books
        Booking b = rideService.bookNearest(eve.getPersonId());
        System.out.println("Eve booked: " + b.getBookingId() +
                " on ride " + b.getRide().getRideId());

        // History
        rideService.getHistory(eve.getPersonId())
                .forEach(h -> System.out.println("  " + h.getBookingId() +
                        " @ " + h.getTimestamp()));

        // Cancel
        rideService.cancelBooking(b.getBookingId());
        System.out.println("After cancellation, history size: " +
                rideService.getHistory(eve.getPersonId()).size());
    }
}
