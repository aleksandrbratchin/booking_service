package ru.bratchin.booking_service.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public abstract class RestTemplateAbstractService implements RestTemplateService {

    @Value("${resttemplate.url.customer}")
    protected String customerURL;

    @Value("${resttemplate.url.room}")
    protected String roomURL;

    @Value("${resttemplate.url.hotel}")
    protected String hotelURL;

    protected final RestTemplate restTemplate;
}
