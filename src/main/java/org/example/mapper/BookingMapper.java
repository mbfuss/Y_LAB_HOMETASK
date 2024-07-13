package org.example.mapper;

import org.example.dto.BookingDTO;
import org.example.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "resourceId", target = "resource.id")
    Booking toEntity(BookingDTO bookingDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "resource.id", target = "resourceId")
    BookingDTO toDTO(Booking booking);
}
