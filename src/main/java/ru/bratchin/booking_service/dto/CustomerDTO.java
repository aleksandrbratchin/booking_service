package ru.bratchin.booking_service.dto;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String lastName,
        String email
) {
}
