package org.example.service.impl;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;
import org.example.repository.BookingRepository;
import org.example.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация интерфейса BookingService для управления бронированиями ресурсов.
 */
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;

    /**
     * Конструктор для инициализации BookingServiceImpl с указанным репозиторием бронирований.
     */
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Создает бронирование ресурса для указанного пользователя на заданное время.
     * Проверяет наличие пересекающихся бронирований.
     */
    public boolean createBooking(User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        // Проверка на наличие пересекающихся бронирований
        if (bookingRepository.existsOverlappingBooking(resource.getId(), startTime, endTime)) {
            System.out.println("The resource is already booked for this time slot.");
            return false;
        }

        // Создание нового бронирования
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        bookingRepository.save(booking);
        System.out.println("Booking successful.");
        return true;
    }

    /**
     * Возвращает список всех бронирований.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Удаляет бронирование по его идентификатору.
     */
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteBookingById(bookingId);
        System.out.println("Booking deleted successfully.");
    }
}