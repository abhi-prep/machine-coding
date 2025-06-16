package repository;

import model.Booking;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BookingRepository implements Repository<String, Booking> {
    private final ConcurrentMap<String,Booking> store = new ConcurrentHashMap<>();
    public void save(Booking b) { store.put(b.getBookingId(), b); }
    public Optional<Booking> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public Collection<Booking> findAll() { return store.values(); }
    public void delete(String id){ store.remove(id); }
}
