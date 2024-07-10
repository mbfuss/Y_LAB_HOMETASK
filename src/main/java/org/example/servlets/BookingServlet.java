package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.config.DatabaseConfig;
import org.example.dto.BookingDTO;
import org.example.mapper.BookingMapper;
import org.example.model.Booking;
import org.example.service.impl.BookingServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {
    private BookingServiceImpl bookingService;
    private ObjectMapper objectMapper;
    private Validator validator;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DatabaseConfig dbConfig = new DatabaseConfig();
            this.bookingService = new BookingServiceImpl(dbConfig.createBookingRepository());
        } catch (IOException e) {
            throw new ServletException("Failed to initialize BookingRepository", e);
        }
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookingDTO bookingDTO = objectMapper.readValue(req.getReader(), BookingDTO.class);
        var violations = validator.validate(bookingDTO);

        if (!violations.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(violations));
            return;
        }

        Booking booking = BookingMapper.INSTANCE.toEntity(bookingDTO);

        if (booking.getResource() == null || booking.getResource().getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Resource cannot be null");
            return;
        }

        if (bookingService.createBooking(booking)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(BookingMapper.INSTANCE.toDTO(booking)));
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write("The resource is already booked for this time slot.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookingDTO> bookings = bookingService.getAllBookings()
                .stream()
                .map(BookingMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(bookings));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookingIdStr = req.getParameter("id");
        if (bookingIdStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Booking ID is required");
            return;
        }

        try {
            Long bookingId = Long.parseLong(bookingIdStr);
            bookingService.deleteBooking(bookingId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid booking ID format");
        }
    }
}

