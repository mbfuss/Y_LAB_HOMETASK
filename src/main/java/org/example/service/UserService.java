package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password, boolean isAdmin) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }
        return user;
    }
}
