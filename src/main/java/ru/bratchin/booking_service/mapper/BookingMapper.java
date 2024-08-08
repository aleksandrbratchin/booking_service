package ru.bratchin.booking_service.mapper;


import org.mapstruct.Mapper;
import ru.bratchin.booking_service.dto.BookingDTO;
import ru.bratchin.booking_service.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO toDTO(Booking booking);

    Booking toEntity(BookingDTO bookingDTO);
}
