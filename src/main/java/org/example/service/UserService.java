package org.example.service;

import org.example.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    User authenticateUser(String username, String password);
    List<User> getAllUsers();
}
