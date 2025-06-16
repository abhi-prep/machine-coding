package repository;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FollowRepository {
    private final ConcurrentMap<String, Set<String>> followers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<String>> followings = new ConcurrentHashMap<>();

    public void follow(String followerId, String followeeId) {
        followings.computeIfAbsent(followerId, k -> ConcurrentHashMap.newKeySet()).add(followeeId);
        followers.computeIfAbsent(followeeId, k -> ConcurrentHashMap.newKeySet()).add(followerId);
    }

    public void unfollow(String followerId, String followeeId) {
        followings.getOrDefault(followerId, Set.of()).remove(followeeId);
        followers.getOrDefault(followeeId, Set.of()).remove(followerId);
    }

    public Set<String> getFollowings(String userId) {
        return followings.getOrDefault(userId, Set.of());
    }
}
