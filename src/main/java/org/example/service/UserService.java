package org.example.service;

import org.example.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервисный класс для управления пользователями.
 */
public class UserService {
    private Map<String, User> users = new HashMap<>();

    /**
     * Регистрирует нового пользователя в системе.
     *
     *  username Имя пользователя (уникальный идентификатор).
     *  password Пароль пользователя.
     *  isAdmin  Флаг, указывающий, является ли пользователь администратором.
     * throws IllegalArgumentException Если пользователь с таким именем уже существует.
     */
    public void registerUser(String username, String password, boolean isAdmin) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(username, new User(username, password, isAdmin));
    }

    /**
     * Аутентифицирует пользователя по его имени пользователя и паролю.
     *
     *  username Имя пользователя для аутентификации.
     *  password Пароль пользователя.
     * return Аутентифицированный пользователь.
     * throws IllegalArgumentException Если имя пользователя или пароль неверные.
     */
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
}
