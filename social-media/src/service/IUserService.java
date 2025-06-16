package service;

import model.User;

// --- Interfaces ---
public interface IUserService {
    void addUser(String userId, String name);

    User getUser(String userId);

    void follow(String followerId, String followeeId);

    void unfollow(String followerId, String followeeId);
}
