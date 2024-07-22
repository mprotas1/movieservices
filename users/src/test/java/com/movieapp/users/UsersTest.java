package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersTest {
    @Mock
    private UserService userService;

    @Test
    void shouldCreateUserWithValidCredentials() {
        UserRegisterRequest request = new UserRegisterRequest("someemail@gmail.com", "somepassword123", "Michał", "Protas");
        UserDTO dto = Mockito.mock(UserDTO.class);
        Mockito.when(userService.register(request)).thenReturn(dto);

        UserDTO register = userService.register(request);
        System.out.println(register);
    }
}
