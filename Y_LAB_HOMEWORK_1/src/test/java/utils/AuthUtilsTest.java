package utils;

import org.example.model.User;
import org.example.utils.AuthUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthUtilsTest {
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User("testuser", "password", false);
    }

    @AfterEach
    public void tearDown() {
        AuthUtils.logout();
    }

    @Test
    public void testLogin() {
        assertNull(AuthUtils.getLoggedInUser());
        AuthUtils.login(testUser);
        assertEquals(testUser, AuthUtils.getLoggedInUser());
    }

    @Test
    public void testLogout() {
        AuthUtils.login(testUser);
        assertNotNull(AuthUtils.getLoggedInUser());
        AuthUtils.logout();
        assertNull(AuthUtils.getLoggedInUser());
    }

    @Test
    public void testGetLoggedInUser() {
        assertNull(AuthUtils.getLoggedInUser());
        AuthUtils.login(testUser);
        assertEquals(testUser, AuthUtils.getLoggedInUser());
    }

    @Test
    public void testIsAdmin() {
        assertFalse(AuthUtils.isAdmin());
        AuthUtils.login(testUser);
        assertFalse(AuthUtils.isAdmin()); // Пользователь не является администратором
        // Создаем нового пользователя с isAdmin = true
        User adminUser = new User("admin", "adminpass", true);
        AuthUtils.login(adminUser);
        assertTrue(AuthUtils.isAdmin()); // Теперь пользователь администратор
    }
}
