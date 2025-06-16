package repository;

import model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// --- In-Memory Repositories ---
public class UserRepository {
    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public boolean exists(String userId) {
        return users.containsKey(userId);
    }
}
