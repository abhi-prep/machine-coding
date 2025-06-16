Design social media network system which allow user to follow friends and add posts and browse post feed.
The system should provide below functionalities:

1. Users must be able to upload posts
2. Users must be able to delete posts
3. Users must be able to browse post feed which would contain the recent 10 posts from his/her account and followings' accounts sorted by time posted
4. Users must be able to follow other users
5. Users must be able to unfollow the users


### Models:
1. model.User
   1. userId
   2. name
   3. posts
   4. followers
   5. following

2. model.Post
   1. postId
   2. content
   3. createdBy
   4. createdAt

### Services:
1. service.impl.UserService
   1. users
   2. addUser()
   3. followUser()
   4. unfollowUser()
   5. getFeed()

2. service.impl.PostService
   3. createPost
   4. deletePost
   5. browsePosts