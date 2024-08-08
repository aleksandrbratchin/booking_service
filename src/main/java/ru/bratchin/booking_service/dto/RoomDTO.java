package ru.bratchin.booking_service.dto;

import java.util.UUID;

public record RoomDTO(
        UUID id,
        String number,
        String type,
        Double price
) {
}
