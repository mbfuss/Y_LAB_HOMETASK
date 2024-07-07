package org.example.dto.mapper;

public class BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingDTO toDTO(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);
}
