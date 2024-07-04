package org.example.service;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    boolean createBooking(User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime);
    void deleteBooking(Long bookingId);
    List<Booking> getAllBookings();

}
