package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.example.model.*;
import org.example.service.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void registerUser_ShouldRegisterNewUser() {
        userService.registerUser("testUser", "password", false);
        User user = userService.authenticateUser("testUser", "password");

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        userService.registerUser("testUser", "password", false);

        assertThatThrownBy(() -> userService.registerUser("testUser", "password", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User already exists");
    }

    @Test
    void authenticateUser_ShouldReturnUser_WhenCredentialsAreCorrect() {
        userService.registerUser("testUser", "password", false);
        User user = userService.authenticateUser("testUser", "password");

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
    }

    @Test
    void authenticateUser_ShouldThrowException_WhenCredentialsAreIncorrect() {
        userService.registerUser("testUser", "password", false);

        assertThatThrownBy(() -> userService.authenticateUser("testUser", "wrongPassword"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid username or password");
    }
}
