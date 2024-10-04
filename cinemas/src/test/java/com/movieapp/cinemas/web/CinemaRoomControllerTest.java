//package com.movieapp.cinemas.web;
//
//import com.movieapp.cinemas.domain.entity.Address;
//import com.movieapp.cinemas.domain.entity.Cinema;
//import com.movieapp.cinemas.service.model.AddressInformation;
//import com.movieapp.cinemas.service.model.CinemaRoomDTO;
//import com.movieapp.cinemas.domain.repository.CinemaRepository;
//import com.movieapp.cinemas.service.CinemaRoomService;
//import com.movieapp.cinemas.testcontainers.Containers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class CinemaRoomControllerTest extends Containers {
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private CinemaRoomService cinemaRoomService;
//    @Autowired
//    private CinemaRepository cinemaRepository;
//
//    private Cinema baseCinema;
//
//    @BeforeEach
//    void setUp() {
//        createBaseCinema();
//    }
//
//    @Test
//    void shouldAddRoomToExistingCinemaAndReturn200() {
//        // when
//        ResponseEntity<CinemaRoomDTO> response = restTemplate.postForEntity("/cinemas/" + baseCinema.getId() + "/rooms/",
//                null,
//                CinemaRoomDTO.class,
//                Map.of("capacity", 100));
//
//        CinemaRoomDTO body = response.getBody();
//        HttpStatusCode statusCode = response.getStatusCode();
//
//        // then
//        assertTrue(statusCode.is2xxSuccessful());
//        assertNotNull(body);
//        assertEquals(100, body.capacity());
//        assertEquals(1, body.number());
//    }
//
//    private void createBaseCinema() {
//        Address address = Address.create(new AddressInformation("Blank Street", "Blank City", "00-000"));
//        Cinema cinema = Cinema.create("Cinema Name", address);
//        baseCinema = cinemaRepository.save(cinema);
//    }
//
//}
