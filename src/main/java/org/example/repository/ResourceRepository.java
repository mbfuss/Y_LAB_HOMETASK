package org.example.repository;

import org.example.model.Resource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceRepository {
    private String url;
    private String username;
    private String password;

    public ResourceRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Resource resource) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO resources (id, name, is_conference_room) VALUES (nextval('resource_seq'), ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, resource.getName());
            statement.setBoolean(2, resource.isConferenceRoom());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Resource findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM resources WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Resource resource = new Resource();
                resource.setId(resultSet.getLong("id"));
                resource.setName(resultSet.getString("name"));
                resource.setConferenceRoom(resultSet.getBoolean("is_conference_room"));
                return resource;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Resource> findAll() {
        List<Resource> resources = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM resources";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
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

    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM resources";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
