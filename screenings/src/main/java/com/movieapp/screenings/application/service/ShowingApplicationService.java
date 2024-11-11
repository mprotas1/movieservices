package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.application.mapper.ScreeningMapper;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import com.movieapp.screenings.domain.service.ScreeningDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ShowingApplicationService implements ScreeningApplicationService {
    private final ScreeningDomainService screeningDomainService;
    private final ScreeningRepository repository;

    @Override
    public ScreeningDTO createScreening(ScreeningCreateRequest request) {
        Screening mapped = ScreeningMapper.toEntity(request);
        Screening screening = screeningDomainService.createScreening(mapped);
        Screening savedScreening = repository.save(screening);
        return ScreeningMapper.toDTO(savedScreening);
    }

    @Override
    public List<ScreeningDTO> findAll() {
        return repository.findAll().stream()
                .map(ScreeningMapper::toDTO)
                .toList();
    }

}
