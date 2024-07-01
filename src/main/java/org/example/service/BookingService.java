package org.example.service;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;
import org.example.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean createBooking(User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        // Проверка пересечения времени бронирования
        if (bookingRepository.existsOverlappingBooking(resource.getId(), startTime, endTime)) {
            // Если ресурс уже забронирован, вывести сообщение и вернуть false
            System.out.println("Ресурс забронирован на данное время");
            return false;
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        bookingRepository.save(booking);
        System.out.println("Ресурс успешно забронирован");
        return true;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
