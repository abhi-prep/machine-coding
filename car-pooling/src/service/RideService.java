package service;

import exception.BookingNotFoundException;
import exception.RideAlreadyExistsException;
import exception.RideNotFoundException;
import exception.SeatUnavailableException;
import model.Booking;
import model.Location;
import model.Person;
import model.Ride;
import repository.BookingRepository;
import repository.PersonRepository;
import repository.RideRepository;
import strategy.DistanceCalculator;
import strategy.EuclideanDistanceCalculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RideService {
    private final RideRepository rideRepo = new RideRepository();
    private final BookingRepository bookingRepo = new BookingRepository();
    private final PersonRepository personRepo = new PersonRepository();
    private final SearchService searchSvc =
            new SearchService(rideRepo, new EuclideanDistanceCalculator());
    // holds last search per person
    private final ConcurrentMap<String,List<Ride>> lastSearch = new ConcurrentHashMap<>();

    public Person registerPerson(String name) {
        Person p = new Person(name);
        personRepo.save(p);
        return p;
    }

    public Ride offerRide(String driverId, double sLat, double sLng,
                          double eLat, double eLng, int seats)
    {
        Person driver = personRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("No such driver"));
        Ride ride = new Ride(driver, new Location(sLat,sLng), new Location(eLat,eLng), seats);
        rideRepo.save(ride);
        return ride;
    }

    public List<Ride> findNearbyRides(String personId, double sLat, double sLng) {
        List<Ride> list = searchSvc.findNearby(sLat, sLng, 5.0);
        lastSearch.put(personId, list);
        return list;
    }

    public Booking bookNearest(String personId) {
        List<Ride> candidates = lastSearch.getOrDefault(personId, List.of());
        Person p = personRepo.findById(personId)
                .orElseThrow(() -> new RuntimeException("No such person"));
        for (Ride r : candidates) {
            if (r.bookSeat()) {
                Booking b = new Booking(p, r);
                bookingRepo.save(b);
                return b;
            }
        }
        throw new SeatUnavailableException("No seats left nearby");
    }

    public void cancelBooking(String bookingId) {
        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        bookingRepo.delete(bookingId);
        b.getRide().cancelSeat();
    }

    public List<Booking> getHistory(String personId) {
        return bookingRepo.findAll().stream()
                .filter(b -> b.getPassenger().getPersonId().equals(personId))
                .sorted(Comparator.comparing(Booking::getTimestamp))
                .collect(Collectors.toList());
    }
}