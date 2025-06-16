import model.Post;
import observer.NotificationService;
import observer.Observable;
import repository.FollowRepository;
import repository.PostRepository;
import repository.UserRepository;
import service.IFeedService;
import service.IPostService;
import service.IUserService;
import service.impl.FeedService;
import service.impl.PostService;
import service.impl.UserService;
import strategy.DefaultFeedSortingStrategy;

import java.util.*;

// --- Driver ---
public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        PostRepository postRepo = new PostRepository();
        FollowRepository followRepo = new FollowRepository();

        IUserService userService = new UserService(userRepo, followRepo);
        IPostService postService = new PostService(postRepo);
        IFeedService feedService = new FeedService(postRepo, followRepo, new DefaultFeedSortingStrategy());

        observer.Observer notificationService = new NotificationService();
        if (postService instanceof Observable) {
            ((observer.Observable) postService).addObserver(notificationService);
        }

        userService.addUser("u1", "Alice");
        userService.addUser("u2", "Bob");
        userService.addUser("u3", "Charlie");

        userService.follow("u1", "u2");
        userService.follow("u1", "u3");

        postService.createPost("u2", "Post 1 from Bob");
        postService.createPost("u2", "Post 2 from Bob");
        postService.createPost("u3", "Post 1 from Charlie");
        postService.createPost("u1", "Post 1 from Alice");

        List<Post> feed = feedService.getFeed("u1");
        System.out.println("Feed for u1:");
        for (Post post : feed) {
            System.out.println(post.getContent() + " [" + post.getUserId() + "]");
        }
    }
}
