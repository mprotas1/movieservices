package com.movieapp.screenings.interfaces.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.screenings.application.dto.PricedSeatDTO;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
class PricingRestClient implements PricingClient {
    private final RestTemplate restTemplate;
    private final String pricingServiceUrl;
    private final ObjectMapper objectMapper;

    PricingRestClient(RestTemplate restTemplate,
                      @Value("${pricing.service.url}") String pricingServiceUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.pricingServiceUrl = pricingServiceUrl;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<PricedSeatDTO> getSeatsPricing(ScreeningDTO screeningDTO) {
        try {
            String screeningJson = objectMapper.writeValueAsString(screeningDTO);
            RequestEntity<String> requestEntity = RequestEntity.post(pricingServiceUrl)
                    .body(screeningJson);
            ResponseEntity<PricedSeatDTO[]> pricedSeatsDTO = restTemplate.exchange(requestEntity, PricedSeatDTO[].class);
            log.debug("Fetched Priced Seats for Screening: {}", screeningDTO.screeningId());
            return List.of(Objects.requireNonNull(pricedSeatsDTO.getBody()));
        }
        catch (HttpClientErrorException httpException) {
            log.error("Caught HttpException during fetching Priced Seat with cause: {}", httpException.getMessage());
            throw new RuntimeException("Could not fetch pricing for Screening: " + screeningDTO.screeningId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
