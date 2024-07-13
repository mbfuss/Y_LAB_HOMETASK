package org.example.service.impl;

import org.example.aspects.AspectExecutor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("userRepository cannot be null");
        }
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        long startTime = System.currentTimeMillis();
        String methodName = "registerUser";
        try {
            AspectExecutor.logMethodStart(methodName);
            userRepository.save(user);
            AspectExecutor.auditAction(methodName, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
    }

    @Override
    public User authenticateUser(String username, String password) {
        long startTime = System.currentTimeMillis();
        String methodName = "authenticateUser";
        try {
            AspectExecutor.logMethodStart(methodName);
            User user = userRepository.findByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid username or password");
            }
            AspectExecutor.auditAction(methodName, username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        long startTime = System.currentTimeMillis();
        String methodName = "getAllUsers";
        try {
            AspectExecutor.logMethodStart(methodName);
            List<User> users = userRepository.findAll();
            AspectExecutor.auditAction(methodName);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return null;
    }
}
