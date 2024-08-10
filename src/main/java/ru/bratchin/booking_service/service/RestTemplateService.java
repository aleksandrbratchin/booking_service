package ru.bratchin.booking_service.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bratchin.booking_service.dto.CustomerDTO;
import ru.bratchin.booking_service.dto.HotelDTO;
import ru.bratchin.booking_service.dto.RoomDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

    @Value("${resttemplate.url.customer}")
    private String customerURL;

    @Value("${resttemplate.url.room}")
    private String roomURL;

    @Value("${resttemplate.url.hotel}")
    private String hotelURL;


    private final RestTemplate restTemplate;

    public CustomerDTO getCustomerById(UUID uuid){
        String url = customerURL + "/" + uuid.toString();
        return restTemplate.getForObject(url, CustomerDTO.class);
    }

    public RoomDTO getRoomById(UUID uuid){
        String url = roomURL + "/" + uuid.toString();
        return restTemplate.getForObject(url, RoomDTO.class);
    }

    public HotelDTO getHotelById(UUID uuid){
        String url = hotelURL + "/" + uuid.toString();
        return restTemplate.getForObject(url, HotelDTO.class);
    }
}
