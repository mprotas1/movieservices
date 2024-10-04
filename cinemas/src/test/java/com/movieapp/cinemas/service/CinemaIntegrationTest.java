//package com.movieapp.cinemas.service;
//
//import com.movieapp.cinemas.domain.entity.Address;
//import com.movieapp.cinemas.domain.entity.Cinema;
//import com.movieapp.cinemas.domain.model.*;
//import com.movieapp.cinemas.domain.repository.CinemaRepository;
//import com.movieapp.cinemas.service.CinemaService;
//import com.movieapp.cinemas.service.model.AddressInformation;
//import com.movieapp.cinemas.service.model.CinemaDTO;
//import com.movieapp.cinemas.service.model.CinemaInformation;
//import com.movieapp.cinemas.testcontainers.Containers;
//import jakarta.persistence.EntityExistsException;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.validation.ConstraintViolationException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class CinemaIntegrationTest extends Containers {
//    @Autowired
//    private CinemaService cinemaService;
//    @Autowired
//    private CinemaRepository cinemaRepository;
//    @Autowired
//    private AddressRepository addressRepository;
//
//    private CinemaInformation basicCinemaInformation;
//
//    @BeforeEach
//    void setUp() {
//        cinemaRepository.deleteAll();
//        addressRepository.deleteAll();
//        basicCinemaInformation = new CinemaInformation("Cinema Name",
//                                              new AddressInformation("Blank Street", "Blank City", "00-000")
//        );
//    }
//
//    @Test
//    @DisplayName("Should create cinema with valid data")
//    void shouldCreateCinemaWithValidData() {
//        CinemaDTO cinema = cinemaService.createCinema(basicCinemaInformation);
//        assertNotNull(cinema);
//        assertNotNull(cinema.id());
//        assertNotNull(cinema.name());
//        assertEquals("00-000 Blank City, Blank Street", cinema.formattedAddress());
//        assertEquals("Cinema Name", cinema.name());;
//    }
//
//    @Test
//    @DisplayName("Should create corresponding address with cinema")
//    @Transactional
//    void shouldCreateCorrespondingAddressWithCinema() {
//        CinemaDTO cinema = cinemaService.createCinema(basicCinemaInformation);
//        Cinema foundCinema = cinemaRepository.findById(cinema.id()).orElseThrow();
//        Address foundAddress = foundCinema.getAddress();
//
//        assertNotNull(foundAddress);
//        assertEquals("Blank Street", foundAddress.street());
//        assertEquals("Blank City", foundAddress.city());
//        assertEquals("00-000", foundAddress.postalCode());
//    }
//
//    @Test
//    @DisplayName("Should not create cinema with existing name")
//    void shouldNotCreateCinemaWithExistingName() {
//        cinemaService.createCinema(basicCinemaInformation);
//        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(basicCinemaInformation));
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideInvalidCinemaInformation")
//    void shouldNotCreateCinemaWithAnyBlankAddressField(CinemaInformation invalidCinemaInformation) {
//        assertThrows(ConstraintViolationException.class, () -> cinemaService.createCinema(invalidCinemaInformation));
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Should find cinema by id")
//    void shouldFindCinemaById() {
//        CinemaDTO cinema = cinemaService.createCinema(basicCinemaInformation);
//        CinemaDTO foundCinema = cinemaService.findById(cinema.id());
//
//        assertNotNull(foundCinema);
//        assertEquals(cinema.id(), foundCinema.id());
//        assertEquals(cinema.name(), foundCinema.name());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Should find cinema by name")
//    void shouldFindCinemaByName() {
//        CinemaDTO cinema = cinemaService.createCinema(basicCinemaInformation);
//        CinemaDTO foundCinema = cinemaService.findByName(cinema.name());
//
//        assertNotNull(foundCinema);
//        assertEquals(cinema.id(), foundCinema.id());
//        assertEquals(cinema.name(), foundCinema.name());
//    }
//
//    @Test
//    @DisplayName("Should not find cinema by non-existing id")
//    void shouldNotFindCinemaByNonExistingId() {
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(1L));
//    }
//
//    @Test
//    @DisplayName("Should not find cinema by non-existing name")
//    void shouldNotFindCinemaByNonExistingName() {
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.findByName("Non-existing Cinema"));
//    }
//
//    @Test
//    @DisplayName("Should delete cinema and cascade delete address by id")
//    void shouldCascadeDeleteAddressById() {
//        CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name",
//                new AddressInformation("Blank Street", "Blank City", "00-000")
//        );
//
//        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);
//        Cinema foundCinema = cinemaRepository.findById(cinema.id()).orElseThrow();
//        Address foundAddress = foundCinema.getAddress();
//
//        cinemaService.deleteById(cinema.id());
//
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(cinema.id()));
//        assertFalse(addressRepository.existsById(foundAddress.getId()));
//    }
//
//    @Test
//    @DisplayName("Should not delete cinema by non-existing id")
//    void shouldNotDeleteCinemaByNonExistingId() {
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.deleteById(1L));
//    }
//
//    @Test
//    @DisplayName("Should not create Cinema with existing address")
//    void shouldNotCreateCinemaWithExistingAddress() {
//        Address address = Address.create(basicCinemaInformation.address());
//        addressRepository.save(address);
//        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(basicCinemaInformation));
//    }
//
//    private static Stream<Arguments> provideInvalidCinemaInformation() {
//        return Stream.of(
//                Arguments.of(new CinemaInformation("Cinema Name 1", new AddressInformation("", "Blank City", "00-000"))),
//                Arguments.of(new CinemaInformation("Cinema Name 2", new AddressInformation("", "", ""))),
//                Arguments.of(new CinemaInformation("Cinema Name 3", new AddressInformation("Blank Street", "", "00-000"))),
//                Arguments.of(new CinemaInformation("Cinema Name 4", new AddressInformation("Blank Street", "Blank City", "")))
//        );
//    }
//
//}
