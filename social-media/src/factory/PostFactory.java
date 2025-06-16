package factory;

import model.Post;

import java.util.UUID;

public class PostFactory {
    public static Post createPost(String userId, String content) {
        return new Post(UUID.randomUUID().toString(), userId, content);
    }
}