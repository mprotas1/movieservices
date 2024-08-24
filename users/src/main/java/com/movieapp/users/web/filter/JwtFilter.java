package com.movieapp.users.web.filter;

import com.movieapp.users.domain.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends BasicAuthenticationFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    private final int TOKEN_INDEX = 7;
    private final String BEARER_PREFIX = "Bearer ";

    public JwtFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<String> token = retrieveTokenFromRequest(request);

        if(token.isEmpty() || !token.get().startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            LOG.debug("The token is either empty or does not start with 'Bearer' pre-fix - returning");
            return;
        }

        String tokenValue = token.get().substring(TOKEN_INDEX);
        String email = getEmail(tokenValue);
        UserDetails user = userDetailsService.loadUserByUsername(email);

        if(isTokenInvalid(tokenValue, user)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        Authentication authenticate = getAuthenticationManager().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        filterChain.doFilter(request, response);
    }

    private Optional<String> retrieveTokenFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"));
    }

    private boolean isTokenInvalid(String token, UserDetails user) {
        return !tokenService.validate(token, user);
    }

    private String getEmail(String token) {
        return tokenService.getSubject(token);
    }

}
