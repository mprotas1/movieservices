package com.movieapp.users.domain;

import com.movieapp.users.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class StandardAuthenticationService implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public UserDTO register(UserRegisterRequest request) {
        log.debug("Registering the user with email address: {}", request.email());
        User user = userMapper.toEntity(request);
        User registered = userRepository.save(user);
        User userWithRole = roleService.addToRole(registered, RoleType.USER);
        return userMapper.toDTO(userWithRole);
    }

    @Override
    public UserDTO authenticate(UserLoginRequest request) {
        log.debug("Authenticating the user with email address: {}", request.email());
        return null;
    }

}
