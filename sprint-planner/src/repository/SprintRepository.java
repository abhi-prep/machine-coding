package repository;

import model.Sprint;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SprintRepository implements ISprintRepository {
    private final Map<String,Sprint> store = new ConcurrentHashMap<>();

    @Override
    public Sprint save(Sprint sprint){
        store.put(sprint.getId(), sprint);
        return sprint;
    }

    @Override
    public Optional<Sprint> findById(String id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public boolean exists(String id) {
        return store.containsKey(id);
    }
}
