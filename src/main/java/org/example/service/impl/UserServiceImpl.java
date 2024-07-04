package org.example.service.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

/**
 * Реализация интерфейса UserService для управления пользователями.
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    /**
     * Конструктор для инициализации UserServiceImpl с указанным репозиторием пользователей.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Регистрирует нового пользователя с указанными параметрами.
     */
    public void registerUser(String username, String password, boolean isAdmin) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        userRepository.save(user);
    }

    /**
     * Аутентифицирует пользователя по указанным имени пользователя и паролю.
     */
    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }
        return user;
    }
}