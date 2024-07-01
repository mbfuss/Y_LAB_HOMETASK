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
        // Check for overlapping bookings
        if (bookingRepository.existsOverlappingBooking(resource.getId(), startTime, endTime)) {
            System.out.println("The resource is already booked for this time slot.");
            return false;
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        bookingRepository.save(booking);
        System.out.println("Booking successful.");
        return true;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteBookingById(bookingId);
        System.out.println("Booking deleted successfully.");
    }
}
