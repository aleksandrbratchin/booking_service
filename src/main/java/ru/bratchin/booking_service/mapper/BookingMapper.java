package ru.bratchin.booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bratchin.booking_service.dto.BookingCreateDTO;
import ru.bratchin.booking_service.dto.BookingRequestDTO;
import ru.bratchin.booking_service.dto.BookingResponseDTO;
import ru.bratchin.booking_service.entity.Booking;
import ru.bratchin.booking_service.service.api.RestTemplateService;

@Mapper(componentModel = "spring")
public abstract class BookingMapper {

    @Autowired
    protected RestTemplateService restTemplateService;

    @Mapping(target = "room", expression = "java(restTemplateService.getRoomById(booking.getRoomId()))")
    @Mapping(target = "hotel", expression = "java(restTemplateService.getHotelById(booking.getHotelId()))")
    @Mapping(target = "customer", expression = "java(restTemplateService.getCustomerById(booking.getCustomerId()))")
    public abstract BookingResponseDTO toDTO(Booking booking);

    public abstract Booking toEntity(BookingRequestDTO bookingDTO);

    public abstract Booking toEntity(BookingCreateDTO bookingDTO);

}
