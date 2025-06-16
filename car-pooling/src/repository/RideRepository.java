package repository;

import exception.RideAlreadyExistsException;
import model.Ride;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RideRepository implements Repository<String, Ride> {
    private final ConcurrentMap<String, Ride> store = new ConcurrentHashMap<>();
    public void save(Ride r) {
        if (store.putIfAbsent(r.getRideId(), r) != null) {
            throw new RideAlreadyExistsException(r.getRideId());
        }
    }
    public Optional<Ride> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public Collection<Ride> findAll() { return store.values(); }
}
