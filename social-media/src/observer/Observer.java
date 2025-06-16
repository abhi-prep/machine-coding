package observer;

import model.Post;

public interface Observer {
    void update(String userId, Post post);
}
