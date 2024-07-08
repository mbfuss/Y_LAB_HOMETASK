package org.example.service;

import org.example.model.Booking;
import org.example.model.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    // Список бронирований
    private List<Booking> bookings = new ArrayList<>();

    // Метод проверки, забронирован ли ресурс в указанный временной интервал
    private boolean isResourceBooked(Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        // Проверяем, есть ли уже другие бронирования на этот ресурс в заданный временной интервал
        return bookings.stream().anyMatch(booking ->
                booking.getResource().equals(resource) &&
                        booking.getStartTime().isBefore(endTime) &&
                        booking.getEndTime().isAfter(startTime));
    }

    // Метод для получения всех бронирований
    public List<Booking> getAllBookings() {
        // Создаем копию списка бронирований, чтобы избежать изменений исходного списка
        return new ArrayList<>(bookings);
    }

    // Метод для добавления нового бронирования
    public void addBooking(Booking booking) {
        // Проверяем, не забронирован ли уже ресурс в указанный временной интервал
        if (isResourceBooked(booking.getResource(), booking.getStartTime(), booking.getEndTime())) {
            throw new IllegalArgumentException("Time slot is already booked");
        }
        // Если проверка прошла успешно, добавляем бронирование в список
        bookings.add(booking);
    }
}
