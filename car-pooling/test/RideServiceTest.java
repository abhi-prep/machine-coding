// ----------- JUnit TESTS --------------

import factory.RideFactory;
import model.Booking;
import org.junit.Test;
import service.InMemoryRideService;
import service.RideService;
import strategy.EuclideanDistanceCalculator;

import static org.junit.Assert.*;

public class RideServiceTest {
    @Test
    public void testAddAndFindNearby() {
//        RideService svc = new InMemoryRideService(new EuclideanDistanceCalculator());
//        svc.addRide(RideFactory.createRide("R1", 0,0,1,1,"D1",2));
//        svc.addRide(RideFactory.createRide("R2", 0.02,0.02,1,1,"D2",2));
//        // search at 0,0 should find both within ~3km
//        var list = svc.findNearbyRides("X","U",0,0,1,1);
//        assertEquals(2, list.size());
//        assertEquals("R1", list.get(0).getRideId());
    }

    @Test
    public void testBookingAndHistory() {
//        RideService svc = new InMemoryRideService(new EuclideanDistanceCalculator());
//        svc.addRide(RideFactory.createRide("R1", 0,0,1,1,"D",1));
//        svc.findNearbyRides("Y","U",0,0,1,1);
//        Booking b = svc.bookNearestRide("Y","U");
//        assertEquals("R1", b.getRideId());
//        assertEquals(1, svc.getRideHistory("Y").size());
//        svc.cancelBooking("Y","R1");
//        assertTrue(svc.getRideHistory("Y").isEmpty());
    }
}
