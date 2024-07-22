package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class CredentialsUserService implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDTO register(@Valid UserRegisterRequest request) {
        log.info("Registering user request: {}", request);
        User userToRegister = User.register(request);
        roleService.addToRole(userToRegister, "USER");
        User registered = userRepository.save(userToRegister);
        UserDTO registeredUserDTO = new UserDTO(registered.getId(), registered.getEmail(), registered.getRoles());
        log.info("User registered: {}", registeredUserDTO);
        return registeredUserDTO;
    }

    @Override
    public UserDTO findById(Long id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getRoles()))
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(user.getId());
    }
}
