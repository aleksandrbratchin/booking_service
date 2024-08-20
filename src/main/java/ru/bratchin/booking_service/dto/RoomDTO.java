package ru.bratchin.booking_service.dto;

import java.util.UUID;
import ru.bratchin.booking_service.dto.enums.TypeDto;

public record RoomDTO(
        UUID id,
        String number,
        TypeDto type,
        Double price
) {
}
