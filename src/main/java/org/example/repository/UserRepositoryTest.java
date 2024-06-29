package org.example.repository;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("coworking")
            .withUsername("custom_user")
            .withPassword("custom_password");

    private UserRepository userRepository;

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("db.url", postgreSQLContainer.getJdbcUrl());
        properties.setProperty("db.username", postgreSQLContainer.getUsername());
        properties.setProperty("db.password", postgreSQLContainer.getPassword());

        userRepository = new UserRepository(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }

    @Test
    public void testSaveAndFindUser() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setAdmin(false);
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("testuser");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("testuser", foundUser.getUsername());
    }
}
