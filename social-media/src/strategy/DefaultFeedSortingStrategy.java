package strategy;

import model.Post;

import java.util.List;

// --- Sorting Strategy ---
public class DefaultFeedSortingStrategy implements FeedSortingStrategy {
    public List<Post> sort(List<Post> posts) {
        posts.sort((a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return posts.size() <= 10 ? posts : posts.subList(0, 10);
    }
}
