package ru.bratchin.booking_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingCreateDTO(
        UUID hotelId,
        UUID roomId,
        UUID customerId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
