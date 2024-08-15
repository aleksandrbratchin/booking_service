package ru.bratchin.booking_service.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bratchin.booking_service.dto.CustomerDTO;
import ru.bratchin.booking_service.dto.HotelDTO;
import ru.bratchin.booking_service.dto.RoomDTO;
import ru.bratchin.booking_service.service.api.RestTemplateAbstractService;

import java.util.UUID;

@Service
//@Profile("dev")
@Profile("prod")
public class RestTemplateDevService extends RestTemplateAbstractService {

    public RestTemplateDevService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public CustomerDTO getCustomerById(UUID uuid) {
        return new CustomerDTO(uuid, "firstName", "lastName", "test@mail.ru");
    }

    @Override
    public RoomDTO getRoomById(UUID uuid) {
        return new RoomDTO(uuid, "15", "testRoom", 100D);
    }

    @Override
    public HotelDTO getHotelById(UUID uuid) {
        return new HotelDTO(uuid, "nameHotel", "adress");
    }
}
