package ru.bratchin.booking_service.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record BookingDTO(
        UUID id,
        UUID hotelId,
        UUID roomId,
        UUID customerId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
