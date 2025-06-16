package service.impl;

import model.Post;
import repository.FollowRepository;
import repository.PostRepository;
import service.IFeedService;
import strategy.FeedSortingStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedService implements IFeedService {
    private final PostRepository postRepo;
    private final FollowRepository followRepo;
    private final FeedSortingStrategy sortingStrategy;

    public FeedService(PostRepository postRepo, FollowRepository followRepo, FeedSortingStrategy strategy) {
        this.postRepo = postRepo;
        this.followRepo = followRepo;
        this.sortingStrategy = strategy;
    }

    public List<Post> getFeed(String userId) {
        Set<String> usersToFetch = new HashSet<>(followRepo.getFollowings(userId));
        usersToFetch.add(userId);

        List<Post> allPosts = new ArrayList<>();
        for (String id : usersToFetch) {
            allPosts.addAll(postRepo.getPosts(id));
        }
        return sortingStrategy.sort(allPosts);
    }
}
