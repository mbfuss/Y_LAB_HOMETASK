package org.example.service;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для управления бронированиями.
 */
public class BookingService {
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Создает новое бронирование, если указанный ресурс доступен в заданный временной интервал.
     *
     *  id        Идентификатор бронирования.
     *  user      Пользователь, совершающий бронирование.
     *  resource  Ресурс (например, комната), который нужно забронировать.
     *  startTime Время начала бронирования.
     *  endTime   Время окончания бронирования.
     * @throws IllegalArgumentException Если временной интервал уже забронирован.
     */
    public void createBooking(String id, User user, Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        if (isResourceBooked(resource, startTime, endTime)) {
            throw new IllegalArgumentException("Time slot is already booked");
        }
        bookings.add(new Booking(id, user, resource, startTime, endTime));
    }

    /**
     * Отменяет бронирование по идентификатору.
     *
     *  id Идентификатор бронирования для отмены.
     */
    public void cancelBooking(String id) {
        bookings.removeIf(booking -> booking.getId().equals(id));
    }

    /**
     * Возвращает список всех текущих бронирований.
     *
     * @return Список всех бронирований.
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    /**
     * Возвращает список бронирований для указанного пользователя.
     *
     *  user Пользователь, для которого нужно найти бронирования.
     * @return Список бронирований данного пользователя.
     */
    public List<Booking> getBookingsByUser(User user) {
        return bookings.stream().filter(booking -> booking.getUser().equals(user)).collect(Collectors.toList());
    }

    /**
     * Возвращает список бронирований для указанного ресурса.
     *
     *  resource Ресурс, для которого нужно найти бронирования.
     * @return Список бронирований для данного ресурса.
     */
    public List<Booking> getBookingsByResource(Resource resource) {
        return bookings.stream().filter(booking -> booking.getResource().equals(resource)).collect(Collectors.toList());
    }

    /**
     * Возвращает список бронирований на указанную дату.
     *
     *  date Дата, на которую нужно найти бронирования.
     * @return Список бронирований на указанную дату.
     */
    public List<Booking> getBookingsByDate(LocalDateTime date) {
        return bookings.stream().filter(booking -> booking.getStartTime().toLocalDate().equals(date.toLocalDate())).collect(Collectors.toList());
    }

    /**
     * Проверяет, забронирован ли указанный ресурс в заданное время.
     *
     *  resource  Ресурс для проверки бронирования.
     *  startTime Время начала интересующего временного интервала.
     *  endTime   Время окончания интересующего временного интервала.
     * @return true, если ресурс забронирован в указанное время, в противном случае - false.
     */
    private boolean isResourceBooked(Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        return bookings.stream().anyMatch(booking -> booking.getResource().equals(resource) &&
                booking.getStartTime().isBefore(endTime) && booking.getEndTime().isAfter(startTime));
    }
}
