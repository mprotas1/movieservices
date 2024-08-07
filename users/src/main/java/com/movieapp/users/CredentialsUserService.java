package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class CredentialsUserService implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO register(@Valid UserRegisterRequest request) {
        log.info("Registering user request: {}", request);
        User userToRegister = userMapper.toEntity(request);
        User registered = userRepository.save(userToRegister);
        roleService.addToRole(registered, RoleType.USER);
        log.info("User registered: {}", registered.getEmail());
        return userMapper.toDTO(registered);
    }

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

}
