package org.example.repository;

import org.example.model.Resource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для работы с ресурсами.
 */
public class ResourceRepository {
    private String url;
    private String username;
    private String password;

    public ResourceRepository() {
    }

    /**
     * Конструктор для инициализации ResourceRepository с указанными параметрами подключения к базе данных.
     */

    public ResourceRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Сохраняет новый ресурс в базе данных.
     */
    public void save(Resource resource) {
        String sql = "INSERT INTO resources (id, name, is_conference_room) VALUES (nextval('resource_seq'), ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, resource.getName());
            statement.setBoolean(2, resource.isConferenceRoom());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает ресурс по его идентификатору.
     */
    public Resource findById(Long id) {
        String sql = "SELECT * FROM resources WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Resource resource = new Resource();
                    resource.setId(resultSet.getLong("id"));
                    resource.setName(resultSet.getString("name"));
                    resource.setConferenceRoom(resultSet.getBoolean("is_conference_room"));
                    return resource;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Возвращает список всех ресурсов из базы данных.
     */
    public List<Resource> findAll() {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT * FROM resources";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Resource resource = new Resource();
                resource.setId(resultSet.getLong("id"));
                resource.setName(resultSet.getString("name"));
                resource.setConferenceRoom(resultSet.getBoolean("is_conference_room"));
                resources.add(resource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }

    /**
     * Удаляет все ресурсы из базы данных.
     */
    public void deleteAll() {
        String sql = "DELETE FROM resources";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}