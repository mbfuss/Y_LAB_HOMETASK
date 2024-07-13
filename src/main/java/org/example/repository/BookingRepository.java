package org.example.repository;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для работы с бронированиями.
 */
public class BookingRepository {
    private String url;
    private String username;
    private String password;

    /**
     * Конструктор для инициализации BookingRepository с указанными параметрами подключения к базе данных.
     */
    public BookingRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Сохраняет новое бронирование в базе данных.
     */
    public void save(Booking booking) {
        String sql = "INSERT INTO bookings (id, user_id, resource_id, start_time, end_time) VALUES (nextval('booking_seq'), ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, booking.getUser().getId());
            statement.setLong(2, booking.getResource().getId());
            statement.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает список всех бронирований из базы данных.
     */
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.id, b.start_time, b.end_time, u.id as user_id, u.username, u.password, u.is_admin, r.id as resource_id, r.name, r.is_conference_room " +
                "FROM bookings b " +
                "JOIN users u ON b.user_id = u.id " +
                "JOIN resources r ON b.resource_id = r.id";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
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

    /**
     * Удаляет все бронирования из базы данных.
     */
    public void deleteAll() {
        String sql = "DELETE FROM bookings";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет наличие пересекающихся бронирований для указанного ресурса на заданный период времени.
     */
    public boolean existsOverlappingBooking(Long resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) > 0 FROM bookings WHERE resource_id = ? AND (? < end_time AND ? > start_time)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, resourceId);
            statement.setTimestamp(2, Timestamp.valueOf(startTime));
            statement.setTimestamp(3, Timestamp.valueOf(endTime));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }


    /**
     * Удаляет бронирование по его идентификатору.
     */
    public void deleteBookingById(Long bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, bookingId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}