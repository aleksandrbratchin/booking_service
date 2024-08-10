package ru.bratchin.booking_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponseDTO(
        UUID id,
        HotelDTO hotel,
        RoomDTO room,
        CustomerDTO customer,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
