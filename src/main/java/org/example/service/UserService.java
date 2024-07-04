package org.example.service;

import org.example.model.User;

public interface UserService {
    void registerUser(String username, String password, boolean isAdmin);
    User authenticateUser(String username, String password);
}
