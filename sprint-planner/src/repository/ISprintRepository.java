package repository;

import model.Sprint;

import java.util.Optional;

public interface ISprintRepository {
    Sprint save(Sprint sprint);
    Optional<Sprint> findById(String id);
    boolean exists(String id);
}