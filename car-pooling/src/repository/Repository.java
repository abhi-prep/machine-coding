package repository;

import java.util.Collection;
import java.util.Optional;

interface Repository<K,V> {
    void save(V v);
    Optional<V> findById(K id);
    Collection<V> findAll();
}