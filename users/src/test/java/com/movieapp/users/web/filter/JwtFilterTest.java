package com.movieapp.users.web.filter;

import com.movieapp.users.domain.service.TokenService;
import com.movieapp.users.testcontainers.TestContainersBase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtFilterTest extends TestContainersBase {
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private JwtFilter jwtFilter;
    private MockHttpServletRequest basicRequest;
    private UserDetails user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JwtFilter(authenticationManager, tokenService, userDetailsService);
        basicRequest = getRequest();
        user = User.withUsername("testUser").password("password").authorities("ROLE_USER").build();
    }

    @Test
    void shouldAuthenticateUserWithValidJWTToken() throws ServletException, IOException {
        basicRequest.addHeader("Authorization", "Bearer validToken");

        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(user);
        when(tokenService.validate(anyString(), eq(user))).thenReturn(true);
        when(tokenService.getSubject(anyString())).thenReturn(user.getUsername());
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        doBasicFilter(jwtFilter, basicRequest);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(user.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());
        assertEquals(user.getAuthorities().size(), SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidJWTToken() throws ServletException, IOException {
        basicRequest.addHeader("Authorization", "Bearer invalidToken");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(tokenService.validate("invalidToken", userDetails)).thenReturn(false);

        doBasicFilter(jwtFilter, basicRequest);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsMissing() throws ServletException, IOException {
        doBasicFilter(jwtFilter, basicRequest);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldNotAuthenticateWhenTokenDoesNotStartWithBearer() throws ServletException, IOException {
        // arrange
        basicRequest.addHeader("Authorization", "notBearer 1243j12iopjfsoikfn2135235jknackaslcjklas");
        doBasicFilter(jwtFilter, basicRequest);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private void doBasicFilter(JwtFilter filter, MockHttpServletRequest request) throws ServletException, IOException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();
        filter.doFilter(request, response, filterChain);
    }

    private MockHttpServletRequest getRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/users");
        request.setMethod("GET");
        return request;
    }

}
