package service;

import org.example.model.*;
import org.example.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    private BookingService bookingService;
    private User mockUser;
    private Resource mockResource;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
        mockUser = Mockito.mock(User.class);
        mockResource = Mockito.mock(Resource.class);

        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockResource.getId()).thenReturn("1");
        when(mockResource.getName()).thenReturn("Desk 1");
    }

    @Test
    void createBooking_ShouldCreateNewBooking() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);

        String bookingId = "1";
        bookingService.createBooking(bookingId, mockUser, mockResource, startTime, endTime);

        List<Booking> bookings = bookingService.getBookings();

        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0).getId()).isEqualTo(bookingId);
        assertThat(bookings.get(0).getUser()).isEqualTo(mockUser);
        assertThat(bookings.get(0).getResource()).isEqualTo(mockResource);
    }

    @Test
    void createBooking_ShouldThrowException_WhenConflictExists() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);

        String bookingId1 = "1";
        String bookingId2 = "2";
        bookingService.createBooking(bookingId1, mockUser, mockResource, startTime, endTime);

        assertThatThrownBy(() -> bookingService.createBooking(bookingId2, mockUser, mockResource, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Time slot is already booked");
    }

    @Test
    void cancelBooking_ShouldRemoveBooking() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);

        String bookingId = "1";
        bookingService.createBooking(bookingId, mockUser, mockResource, startTime, endTime);
        bookingService.cancelBooking(bookingId);

        List<Booking> bookings = bookingService.getBookings();

        assertThat(bookings).isEmpty();
    }



    @Test
    void getBookingsByResource_ShouldReturnBookingsForResource() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);

        String bookingId = "1";
        bookingService.createBooking(bookingId, mockUser, mockResource, startTime, endTime);

        List<Booking> bookings = bookingService.getBookingsByResource(mockResource);

        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0).getId()).isEqualTo(bookingId);
        assertThat(bookings.get(0).getResource()).isEqualTo(mockResource);
    }
}