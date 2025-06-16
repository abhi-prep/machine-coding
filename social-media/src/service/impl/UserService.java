package service.impl;

import exception.UserNotFoundException;
import model.User;
import repository.FollowRepository;
import repository.UserRepository;
import service.IUserService;

// --- Services ---
public class UserService implements IUserService {
    private final UserRepository userRepo;
    private final FollowRepository followRepo;

    public UserService(UserRepository userRepo, FollowRepository followRepo) {
        this.userRepo = userRepo;
        this.followRepo = followRepo;
    }

    public void addUser(String userId, String name) {
        userRepo.addUser(new User(userId, name));
    }

    @Override
    public User getUser(String userId) {
        if (!userRepo.exists(userId)) {
            throw new UserNotFoundException("Invalid user ID provided.");
        }
        return userRepo.getUser(userId);
    }

    public void follow(String followerId, String followeeId) {
        if (!userRepo.exists(followerId) || !userRepo.exists(followeeId)) {
            throw new UserNotFoundException("Invalid user IDs provided.");
        }
        followRepo.follow(followerId, followeeId);
    }

    public void unfollow(String followerId, String followeeId) {
        followRepo.unfollow(followerId, followeeId);
    }

}
