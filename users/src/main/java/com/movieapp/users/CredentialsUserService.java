package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialsUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO register(UserRegisterRequest request) {
        log.info("Registering user request: {}", request);
        User userToRegister = User.register(request);
        User registered = userRepository.save(userToRegister);
        return new UserDTO(registered.getId(), registered.getEmail());
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getEmail()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(user.getId());
    }
}
