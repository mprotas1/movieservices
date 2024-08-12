package com.movieapp.users.domain;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public interface TokenService {
    String generateToken(UserDetails subject);
    String getSubject(String token);
    Date getExpiration(String token);
    List<GrantedAuthority> getAuthorities(String token);
    Object extractClaim(String token, Function<Claims, ?> resolver);
    boolean validate(String token, UserDetails userDetails);
    boolean isExpired(String token);
}
