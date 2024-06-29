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

    public void createBooking(User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
