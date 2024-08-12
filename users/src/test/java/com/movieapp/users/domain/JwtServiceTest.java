package com.movieapp.users.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @Mock
    private JwtService jwtTokenService;

    @Mock
    private UserDetails userDetails;

    @Test
    void shouldGenerateToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtTokenService.generateToken(userDetails)).thenCallRealMethod();

        String token = jwtTokenService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateToken() {
        String token = "validToken";
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtTokenService.validate(token, userDetails)).thenCallRealMethod();
        when(jwtTokenService.getSubject(token)).thenReturn("testUser");
        when(jwtTokenService.isExpired(token)).thenReturn(false);

        boolean isValid = jwtTokenService.validate(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void shouldNotValidateTokenWhenExpired() {
        String token = "expiredToken";
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtTokenService.validate(token, userDetails)).thenCallRealMethod();
        when(jwtTokenService.getSubject(token)).thenReturn("testUser");
        when(jwtTokenService.isExpired(token)).thenReturn(true);

        boolean isValid = jwtTokenService.validate(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    void shouldExtractSubject() {
        String token = "token";
        when(jwtTokenService.getSubject(token)).thenCallRealMethod();
        when(jwtTokenService.extractClaim(eq(token), any(Function.class))).thenReturn("testUser");

        String subject = jwtTokenService.getSubject(token);

        assertEquals("testUser", subject);
    }

    @Test
    void shouldExtractExpiration() {
        String token = "token";
        Date expirationDate = new Date();
        when(jwtTokenService.getExpiration(token)).thenCallRealMethod();
        when(jwtTokenService.extractClaim(eq(token), any(Function.class))).thenReturn(expirationDate);

        Date extractedDate = jwtTokenService.getExpiration(token);

        assertEquals(expirationDate, extractedDate);
    }

    @Test
    void shouldCheckIfTokenIsExpired() {
        String token = "token";
        Date pastDate = new Date(System.currentTimeMillis() - 1000);
        when(jwtTokenService.getExpiration(token)).thenReturn(pastDate);
        when(jwtTokenService.isExpired(token)).thenCallRealMethod();

        boolean isExpired = jwtTokenService.isExpired(token);

        assertTrue(isExpired);
    }

    @Test
    void shouldCheckIfTokenIsNotExpired() {
        String token = "token";
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        when(jwtTokenService.getExpiration(token)).thenReturn(futureDate);
        when(jwtTokenService.isExpired(token)).thenCallRealMethod();

        boolean isExpired = jwtTokenService.isExpired(token);

        assertFalse(isExpired);
    }

    @Test
    void shouldExtractAuthorities() {
        String token = "mockedToken";
        List<GrantedAuthority> expectedAuthorities = List.of(() -> "ROLE_USER");

        when(jwtTokenService.getAuthorities(token)).thenCallRealMethod();
        when(jwtTokenService.extractClaim(eq(token), any(Function.class))).thenReturn(expectedAuthorities);

        List<GrantedAuthority> authorities = jwtTokenService.getAuthorities(token);

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.get(0).getAuthority());
    }
}