package service;

import model.Post;

import java.util.List;

public interface IPostService {
    Post createPost(String userId, String content);

    void deletePost(String userId, String postId);

    List<Post> getPosts(String userId);
}
