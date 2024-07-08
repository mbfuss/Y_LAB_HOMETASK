package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookingDTO;
import org.example.mapper.BookingMapper;
import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;
import org.example.service.BookingService;
import org.example.service.ResourceService;
import org.example.service.UserService;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final BookingService bookingService = new BookingService();
    private final ResourceService resourceService = new ResourceService();
    private final UserService userService = new UserService();

    /**
     * Обрабатывает GET-запросы HTTP для получения списка всех бронирований.
     * Возвращает JSON-объект, содержащий список всех бронирований.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Booking> bookings = bookingService.getAllBookings();
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), bookings);
    }

    /**
     * Обрабатывает POST-запросы HTTP для добавления нового бронирования.
     * Ожидает JSON-объект BookingDTO в теле запроса.
     * При успешном добавлении бронирования возвращает статус 201 (Создано), иначе возвращает статус 400 (Плохой запрос).
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookingDTO bookingDTO = objectMapper.readValue(req.getInputStream(), BookingDTO.class);
        try {
            User user = userService.getUserById(bookingDTO.getUserId());
            Resource resource = resourceService.getResourceById(bookingDTO.getResourceId());
            LocalDateTime startTime = bookingDTO.getStartTime();
            LocalDateTime endTime = bookingDTO.getEndTime();

            Booking booking = BookingMapper.INSTANCE.bookingDTOToBooking(bookingDTO);
            bookingService.addBooking(booking);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
