//package com.movieapp.cinemas.service;
//
//import com.movieapp.cinemas.service.model.AddressInformation;
//import com.movieapp.cinemas.service.model.CinemaInformation;
//import com.movieapp.cinemas.service.model.CinemaRoomInformation;
//import com.movieapp.cinemas.domain.repository.CinemaRepository;
//import com.movieapp.cinemas.service.CinemaRoomService;
//import com.movieapp.cinemas.testcontainers.Containers;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//class CinemaRoomTest extends Containers {
//    @Autowired
//    private CinemaRoomService cinemaService;
//    @Autowired
//    private CinemaRepository cinemaRepository;
//
//    private final AddressInformation validAddressInformation = new AddressInformation("Movie Street 6", "Hollywood", "123-312");
//    private final CinemaInformation validCinemaInformation = new CinemaInformation("Movie Theatre", validAddressInformation);
//
//    @BeforeEach
//    void setUp() {
//        cinemaRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("Should not create cinema room in non-existing cinema")
//    @Transactional
//    void shouldNotCreateCinemaRoomInNonExistingCinema() {
//        CinemaRoomInformation cinemaRoomInformation = new CinemaRoomInformation(999L, 100);
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.addRoom(cinemaRoomInformation));
//    }
//
//}
