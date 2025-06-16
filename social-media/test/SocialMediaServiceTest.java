import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FollowRepository;
import repository.UserRepository;
import service.IUserService;
import service.impl.UserService;

import static org.junit.jupiter.api.Assertions.*;


public class SocialMediaServiceTest {

    private IUserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new UserRepository(), new FollowRepository());
    }

    @Test
    void testUserCreationAndFetch() {
        userService.addUser("u1", "Alice");
        assertEquals("Alice", userService.getUser("u1").getName());
    }

}
