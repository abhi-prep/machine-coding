package observer;

import model.Post;

public class NotificationService implements Observer {
    @Override
    public void update(String userId, Post post) {
        System.out.println("Notify followers of user " + userId + " about new post: " + post.getContent());
    }
}