package org.example.repository;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private String url;
    private String username;
    private String password;

    public BookingRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Booking booking) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO bookings (id, user_id, resource_id, start_time, end_time) VALUES (nextval('booking_seq'), ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, booking.getUser().getId());
            statement.setLong(2, booking.getResource().getId());
            statement.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT b.id, b.start_time, b.end_time, u.id as user_id, u.username, u.password, u.is_admin, r.id as resource_id, r.name, r.is_conference_room " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN resources r ON b.resource_id = r.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getLong("id"));
                booking.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                booking.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());

                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                booking.setUser(user);

                Resource resource = new Resource();
                resource.setId(resultSet.getLong("resource_id"));
                resource.setName(resultSet.getString("name"));
                resource.setConferenceRoom(resultSet.getBoolean("is_conference_room"));
                booking.setResource(resource);

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM bookings";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsOverlappingBooking(Long resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        boolean exists = false;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT COUNT(*) > 0 FROM bookings WHERE resource_id = ? AND (? < end_time AND ? > start_time)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, resourceId);
            statement.setTimestamp(2, Timestamp.valueOf(startTime));
            statement.setTimestamp(3, Timestamp.valueOf(endTime));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

}
