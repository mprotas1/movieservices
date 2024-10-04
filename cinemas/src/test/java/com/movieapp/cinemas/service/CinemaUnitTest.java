//package com.movieapp.cinemas.service;
//
//import com.movieapp.cinemas.domain.entity.Address;
//import com.movieapp.cinemas.domain.entity.Cinema;
//import com.movieapp.cinemas.service.model.AddressInformation;
//import com.movieapp.cinemas.service.model.CinemaDTO;
//import com.movieapp.cinemas.service.model.CinemaInformation;
//import com.movieapp.cinemas.domain.repository.CinemaRepository;
//import jakarta.persistence.EntityExistsException;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CinemaUnitTest {
//    @InjectMocks
//    private TheatreService cinemaService;
//    @Mock
//    private CinemaRepository cinemaRepository;
//
//    AddressInformation exampleAddressInformation = new AddressInformation("Al. Wyzwolenia", "Szczecin", "71-210");
//    CinemaInformation cinemaInformation = new CinemaInformation("Cinema Name", exampleAddressInformation);
//    Address exampleAddress = Address.create(exampleAddressInformation);
//    Cinema exampleCinema = Cinema.create(cinemaInformation.name(), exampleAddress);
//
//    @Test
//    void shouldCreateCinemaWithValidData() {
//        exampleCinema.setId(1L);
//
//        when(cinemaRepository.save(any())).thenReturn(exampleCinema);exampleCinema
//
//        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);
//
//        assertNotNull(cinema);
//        assertEquals("71-210 Szczecin, Al. Wyzwolenia", cinema.formattedAddress());
//        assertFalse(cinema.id().toString().isEmpty());
//        assertFalse(cinema.name().isEmpty());
//
//        verify(cinemaRepository, times(1)).findByName(cinemaInformation.name());
//        verify(addressService, times(1)).save(exampleAddressInformation);
//    }
//
//    @Test
//    void shouldNotCreateCinemaWithExistingName() {
//        exampleCinema.setId(1L);
//        when(cinemaRepository.findByName(any())).thenReturn(Optional.of(exampleCinema));
//        assertThrows(EntityExistsException.class, () -> cinemaService.createCinema(cinemaInformation));
//        verify(cinemaRepository, times(1)).findByName(cinemaInformation.name());
//    }
//
//    @Test
//    void shouldFindCinemaById() {
//        Long id = 1L;
//        exampleCinema.setId(id);
//        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
//        CinemaDTO cinema = cinemaService.findById(id);
//        assertNotNull(cinema);
//        assertEquals(id, cinema.id());
//        verify(cinemaRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void shouldNotFindCinemaByNonExistingId() {
//        Long id = 1L;
//        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.findById(id));
//        verify(cinemaRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void shouldFindCinemaByName() {
//        String name = "Cinema Name";
//        exampleCinema.setId(1L);
//        when(cinemaRepository.findByName(any())).thenReturn(Optional.of(exampleCinema));
//        CinemaDTO cinema = cinemaService.findByName(name);
//        assertNotNull(cinema);
//        assertEquals(name, cinema.name());
//        verify(cinemaRepository, times(1)).findByName(name);
//    }
//
//    @Test
//    void shouldNotFindCinemaByNonExistingName() {
//        String name = "Cinema Name";
//        when(cinemaRepository.findByName(name)).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.findByName(name));
//        verify(cinemaRepository, times(1)).findByName(name);
//    }
//
//    @Test
//    void shouldDeleteCinemaById() {
//        Long id = 1L;
//        exampleCinema.setId(id);
//        when(cinemaRepository.findById(any())).thenReturn(Optional.of(exampleCinema));
//        assertDoesNotThrow(() -> cinemaService.deleteById(id));
//
//        verify(cinemaRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void shouldNotDeleteCinemaByNonExistingId() {
//        Long id = 1L;
//        when(cinemaRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> cinemaService.deleteById(id));
//
//        verify(cinemaRepository, times(1)).findById(id);
//    }
//
//}
