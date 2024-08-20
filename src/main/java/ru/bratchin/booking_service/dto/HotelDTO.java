package ru.bratchin.booking_service.dto;

import java.util.UUID;

public record HotelDTO(
        UUID id,
        String name,
        String address
) {
}
