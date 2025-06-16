package service;

import model.Post;

import java.util.List;

public interface IFeedService {
    List<Post> getFeed(String userId);
}
