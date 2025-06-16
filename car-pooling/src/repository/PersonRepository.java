package repository;

import model.Person;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PersonRepository implements Repository<String, Person> {
    private final ConcurrentMap<String,Person> store = new ConcurrentHashMap<>();
    public void save(Person p) { store.put(p.getPersonId(), p); }
    public Optional<Person> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public Collection<Person> findAll() { return store.values(); }
}