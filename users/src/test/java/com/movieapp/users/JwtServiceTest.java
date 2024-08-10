package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @Test
    void shouldGenerateToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtService.generateToken(userDetails)).thenCallRealMethod();

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(!token.isEmpty());
    }

    @Test
    void shouldValidateToken() {
        String token = "validToken";
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtService.validateToken(token, userDetails)).thenCallRealMethod();
        when(jwtService.extractSubject(token)).thenReturn("testUser");
        when(jwtService.isTokenExpired(token)).thenReturn(false);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void shouldNotValidateTokenWhenExpired() {
        String token = "expiredToken";
        when(userDetails.getUsername()).thenReturn("testUser");
        when(jwtService.validateToken(token, userDetails)).thenCallRealMethod();
        when(jwtService.extractSubject(token)).thenReturn("testUser");
        when(jwtService.isTokenExpired(token)).thenReturn(true);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    void shouldExtractSubject() {
        String token = "token";
        when(jwtService.extractSubject(token)).thenCallRealMethod();
        when(jwtService.extractClaim(eq(token), any(Function.class))).thenReturn("testUser");

        String subject = jwtService.extractSubject(token);

        assertEquals("testUser", subject);
    }

    @Test
    void shouldExtractExpiration() {
        String token = "token";
        Date expirationDate = new Date();
        when(jwtService.extractExpiration(token)).thenCallRealMethod();
        when(jwtService.extractClaim(eq(token), any(Function.class))).thenReturn(expirationDate);

        Date extractedDate = jwtService.extractExpiration(token);

        assertEquals(expirationDate, extractedDate);
    }

    @Test
    void shouldCheckIfTokenIsExpired() {
        String token = "token";
        Date pastDate = new Date(System.currentTimeMillis() - 1000);
        when(jwtService.extractExpiration(token)).thenReturn(pastDate);
        when(jwtService.isTokenExpired(token)).thenCallRealMethod();

        boolean isExpired = jwtService.isTokenExpired(token);

        assertTrue(isExpired);
    }

    @Test
    void shouldCheckIfTokenIsNotExpired() {
        String token = "token";
        Date futureDate = new Date(System.currentTimeMillis() + 1000);
        when(jwtService.extractExpiration(token)).thenReturn(futureDate);
        when(jwtService.isTokenExpired(token)).thenCallRealMethod();

        boolean isExpired = jwtService.isTokenExpired(token);

        assertFalse(isExpired);
    }
}