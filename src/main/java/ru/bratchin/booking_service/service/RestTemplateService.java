package ru.bratchin.booking_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.bratchin.booking_service.dto.CustomerDTO;
import ru.bratchin.booking_service.dto.HotelDTO;
import ru.bratchin.booking_service.dto.RoomDTO;
import ru.bratchin.booking_service.service.api.RestTemplateAbstractService;

import java.util.UUID;

@Service
public class RestTemplateService extends RestTemplateAbstractService {

    public RestTemplateService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public CustomerDTO getCustomerById(UUID uuid) {
        String url = customerURL + "/" + uuid.toString();
        try {
            return restTemplate.getForObject(url, CustomerDTO.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw e;
            }
        } catch (RestClientException e) {
            throw e;
        }
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
