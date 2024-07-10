package org.example.service.impl;

import org.example.aspects.AspectExecutor;
import org.example.model.Booking;
import org.example.repository.BookingRepository;
import org.example.service.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean createBooking(Booking booking) {
        long startTime = System.currentTimeMillis();
        String methodName = "createBooking";
        if (booking.getResource() == null) {
            throw new IllegalArgumentException("Resource cannot be null");
        }
        try {
            AspectExecutor.logMethodStart(methodName);
            if (bookingRepository.existsOverlappingBooking(booking.getResource().getId(), booking.getStartTime(), booking.getEndTime())) {
                System.out.println("Found overlapping booking.");
                return false;
            }
            bookingRepository.save(booking);
            AspectExecutor.auditAction(methodName, booking);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return false;
    }

    public List<Booking> getAllBookings() {
        long startTime = System.currentTimeMillis();
        String methodName = "getAllBookings";
        try {
            AspectExecutor.logMethodStart(methodName);
            List<Booking> bookings = bookingRepository.findAll();
            AspectExecutor.auditAction(methodName);
            return bookings;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        return null;
    }

    public void deleteBooking(Long bookingId) {
        long startTime = System.currentTimeMillis();
        String methodName = "deleteBooking";
        try {
            AspectExecutor.logMethodStart(methodName);
            bookingRepository.deleteBookingById(bookingId);
            AspectExecutor.auditAction(methodName, bookingId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            AspectExecutor.logMethodEnd(methodName, duration);
        }
        System.out.println("Booking deleted successfully.");
    }
}
