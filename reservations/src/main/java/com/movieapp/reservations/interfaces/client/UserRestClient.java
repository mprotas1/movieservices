package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class UserRestClient implements UserClient {
    @Value("${services.url.users}")
    private final String USER_SERVICE_URL;
    private final RestTemplate restTemplate;

    @Override
    public Optional<UserDTO> getUser(Long id) {
        log.debug("Getting user with id: {}", id);
        try {
            UserDTO user = restTemplate.getForEntity(USER_SERVICE_URL + "/" + id, UserDTO.class).getBody();
            return Optional.ofNullable(user);
        }
        catch (Exception e) {
            log.error("Error while getting user with id: {}", id, e);
            return Optional.empty();
        }
    }

}
