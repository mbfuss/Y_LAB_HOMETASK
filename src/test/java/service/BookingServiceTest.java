package service;

import org.example.model.Booking;
import org.example.model.Resource;
import org.example.model.User;
import org.example.repository.BookingRepository;
import org.example.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;

    private User user;
    private Resource resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        resource = new Resource();
        resource.setId(1L);
        startTime = LocalDateTime.of(2024, 7, 1, 10, 0);
        endTime = LocalDateTime.of(2024, 7, 1, 12, 0);
    }

    @Test
    public void testCreateBooking_Success() {
        when(bookingRepository.existsOverlappingBooking(resource.getId(), startTime, endTime)).thenReturn(false);

        boolean result = bookingServiceImpl.createBooking(user, resource, startTime, endTime);

        assertThat(result).isTrue();
        verify(bookingRepository, times(1)).save(any(Booking.class));
        System.out.println("Ресурс успешно забронирован");
    }

    @Test
    public void testCreateBooking_FailureDueToConflict() {
        when(bookingRepository.existsOverlappingBooking(resource.getId(), startTime, endTime)).thenReturn(true);

        boolean result = bookingServiceImpl.createBooking(user, resource, startTime, endTime);

        assertThat(result).isFalse();
        verify(bookingRepository, never()).save(any(Booking.class));
        System.out.println("Ресурс забронирован на данное время");
    }

    @Test
    public void testGetAllBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingServiceImpl.getAllBookings();

        assertThat(result).hasSize(2);
        assertThat(result).contains(booking1, booking2);
        verify(bookingRepository, times(1)).findAll();
    }
}
