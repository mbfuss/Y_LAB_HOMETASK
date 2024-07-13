package org.example.service;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    boolean createBooking(Booking booking);
    void deleteBooking(Long bookingId);
    List<Booking> getAllBookings();

}
