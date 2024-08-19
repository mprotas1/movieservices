package com.movieapp.users.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
class JwtService implements TokenService {
    private final String SECRET = "05beb010911e5fb6493e25054f561002875b5eb59966837a8654271609da2a88";
    private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 60;
    private final String AUTHORITIES_KEY = "authorities";

    @Override
    public String generateToken(UserDetails subject) {
        return Jwts.builder()
                .setSubject(subject.getUsername())
                .setExpiration(setExpirationDate())
                .setIssuedAt(now())
                .claim(AUTHORITIES_KEY, subject.getAuthorities())
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String getSubject(String token) {
        return (String) extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpiration(String token) {
        return (Date) extractClaim(token, Claims::getExpiration);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String token) {
        return (List<GrantedAuthority>) extractClaim(token, claims -> claims.get(AUTHORITIES_KEY, List.class));
    }

    @Override
    public boolean validate(String token, UserDetails user) {
        return isUserValid(token, user) && !isExpired(token);
    }

    @Override
    public boolean isExpired(String token) {
        return getExpiration(token).before(now());
    }

    @Override
    public Object extractClaim(String token, Function<Claims, ?> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isUserValid(String token, UserDetails user) {
        String subject = getSubject(token);
        String attempt = user.getUsername();
        return subject.equals(attempt);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date now() {
        return new Date(System.currentTimeMillis());
    }

    private Date setExpirationDate() {
        Date now = new Date();
        return new Date(now.getTime() + this.JWT_TOKEN_VALIDITY);
    }

}
