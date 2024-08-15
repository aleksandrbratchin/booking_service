package ru.bratchin.booking_service.service.api;

import ru.bratchin.booking_service.dto.CustomerDTO;
import ru.bratchin.booking_service.dto.HotelDTO;
import ru.bratchin.booking_service.dto.RoomDTO;

import java.util.UUID;

public interface RestTemplateService {
    CustomerDTO getCustomerById(UUID uuid);

    RoomDTO getRoomById(UUID uuid);

    HotelDTO getHotelById(UUID uuid);
}
