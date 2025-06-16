import model.Booking;
import model.Person;
import model.Ride;
import service.RideService;


public class Main {
    public static void main(String[] args) {
        RideService svc = new RideService();

        // register users
        Person alice = svc.registerPerson("Alice");
        Person bob   = svc.registerPerson("Bob");
        Person eve   = svc.registerPerson("Eve");

        // Alice and Bob offer rides
        Ride r1 = svc.offerRide(alice.getPersonId(), 12.96,77.59, 12.98,77.60, 3);
        Ride r2 = svc.offerRide(bob.getPersonId(),   12.97,77.58, 12.99,77.61, 2);

        // Eve searches nearby
        var nearby = svc.findNearbyRides(eve.getPersonId(), 12.965, 77.585);
        System.out.println("Nearby rides: " +
                nearby.stream().map(Ride::getRideId).toList());

        // Eve books
        Booking b = svc.bookNearest(eve.getPersonId());
        System.out.println("Eve booked: " + b.getBookingId() +
                " on ride " + b.getRide().getRideId());

        // History
        svc.getHistory(eve.getPersonId())
                .forEach(h -> System.out.println("  " + h.getBookingId() +
                        " @ " + h.getTimestamp()));

        // Cancel
        svc.cancelBooking(b.getBookingId());
        System.out.println("After cancellation, history size: " +
                svc.getHistory(eve.getPersonId()).size());
    }
}
