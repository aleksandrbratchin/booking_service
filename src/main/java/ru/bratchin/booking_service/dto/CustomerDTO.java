package ru.bratchin.booking_service.dto;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
