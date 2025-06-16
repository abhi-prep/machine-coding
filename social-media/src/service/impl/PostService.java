package service.impl;

import factory.PostFactory;
import model.Post;
import observer.Observable;
import observer.Observer;
import repository.PostRepository;
import service.IPostService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostService implements IPostService, Observable {
    private final PostRepository postRepo;
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    public PostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public Post createPost(String userId, String content) {
        Post post = PostFactory.createPost(userId, content);
        postRepo.addPost(post);
        notifyObservers(userId, post);
        return post;
    }

    public void deletePost(String userId, String postId) {
        postRepo.deletePost(userId, postId);
    }

    public List<Post> getPosts(String userId) {
        return postRepo.getPosts(userId);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String userId, Post post) {
        for (Observer observer : observers) {
            observer.update(userId, post);
        }
    }
}
