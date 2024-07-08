package org.example.service;

import org.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    // Список пользователей
    private List<User> users = new ArrayList<>();

    // Конструктор для добавления примерных пользователей
    public UserService() {
        // Пример добавления пользователей при инициализации сервиса
        users.add(new User("1", "admin", "password", true));
        users.add(new User("2", "user", "password", false));
    }

    // Метод для получения пользователя по его идентификатору
    public User getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Метод для регистрации нового пользователя
    public void registerUser(String username, String password, boolean isAdmin) {
        // Проверяем, не существует ли уже пользователь с таким же именем
        if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
            throw new IllegalArgumentException("User already exists");
        }
        // Создаем нового пользователя и добавляем его в список
        User newUser = new User(String.valueOf(users.size() + 1), username, password, isAdmin);
        users.add(newUser);
    }

    // Метод для аутентификации пользователя
    public User authenticateUser(String username, String password) {
        // Ищем пользователя по имени пользователя и паролю
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }
}
