package org.example.repository;

import org.example.model.User;

import java.sql.*;

    public class UserRepository {
        private String url;
        private String username;
        private String password;

        public UserRepository(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public void save(User user) {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO coworking.users (id, username, password, is_admin) VALUES (nextval('user_seq'), ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setBoolean(3, user.isAdmin());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public User findByUsername(String username) {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM coworking.users WHERE username = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("is_admin"));
                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void deleteAll() {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM coworking.users";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
