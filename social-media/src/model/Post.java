package model;

public class Post {
    private final String postId;
    private final String userId;
    private final String content;
    private final long createdAt;

    public Post(String postId, String userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = System.currentTimeMillis();
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
