package repository;

import model.Post;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

public class PostRepository {
    private final ConcurrentMap<String, Deque<Post>> userPosts = new ConcurrentHashMap<>();

    public void addPost(Post post) {
        userPosts.computeIfAbsent(post.getUserId(), k -> new ConcurrentLinkedDeque<>()).addFirst(post);
    }

    public List<Post> getPosts(String userId) {
        return new ArrayList<>(userPosts.getOrDefault(userId, new ConcurrentLinkedDeque<>()));
    }

    public void deletePost(String userId, String postId) {
        Deque<Post> posts = userPosts.get(userId);
        if (posts != null) {
            posts.removeIf(p -> p.getPostId().equals(postId));
        }
    }
}
