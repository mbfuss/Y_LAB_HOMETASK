package org.example.repository;

import org.example.model.User;

import java.sql.*;

/**
 * Репозиторий для работы с пользователями.
 */
public class UserRepository {
    private String url;
    private String username;
    private String password;

    /**
     * Конструктор для инициализации UserRepository с указанными параметрами подключения к базе данных.
     */
    public UserRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Сохраняет нового пользователя в базе данных.
     */
    public void save(User user) {
        String sql = "INSERT INTO users (id, username, password, is_admin) VALUES (nextval('user_seq'), ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isAdmin());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает пользователя по его имени пользователя.
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(url, this.username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("is_admin"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Удаляет всех пользователей из базы данных.
     */
    public void deleteAll() {
        String sql = "DELETE FROM users";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}