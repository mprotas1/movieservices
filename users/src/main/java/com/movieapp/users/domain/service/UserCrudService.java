package com.movieapp.users.domain.service;

import com.movieapp.users.domain.mapper.UserMapper;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class UserCrudService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findById(Long id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find user with id: " + id + " to delete."));
        userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public UserDTO update(@Positive Long userId, @Valid UserUpdateRequest userData) {
        log.debug("Attempting to update user with id: {} with data: {}", userId, userData);
        User toUpdate = userRepository.findById(userId)
                .map(user -> userMapper.updateEntity(user, userData))
                .orElseThrow(() -> new EntityNotFoundException("Could not find user with id: " + userId + " to update."));
        User updated = userRepository.save(toUpdate);
        log.debug("Successfully updated user with id: {}", userId);
        return userMapper.toDTO(updated);
    }

}
