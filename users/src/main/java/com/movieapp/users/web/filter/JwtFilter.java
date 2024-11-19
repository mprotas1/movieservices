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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private final int TOKEN_INDEX = 7;
    private final String BEARER_PREFIX = "Bearer ";

    public JwtFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<String> token = retrieveTokenFromRequest(request);

        if(request.getServletPath().contains("/auth")) {
            LOG.debug("The request path contains 'auth' - passing through the filter chain");
            filterChain.doFilter(request, response);
            return;
        }

        if(token.isEmpty() || !token.get().startsWith(BEARER_PREFIX)) {
            LOG.debug("The token is either empty or does not start with 'Bearer' pre-fix - returning");
            filterChain.doFilter(request, response);
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
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
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
