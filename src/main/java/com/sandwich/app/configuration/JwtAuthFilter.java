package com.sandwich.app.configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

import com.sandwich.app.service.auth.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            var token = getJwtToken(request);

            if (token == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            if (tokenService.validateJwtToken(token)) {
                var userDetails = userDetailsService.loadUserByUsername(tokenService.getSubject(token));
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        var bearer = request.getHeader(AUTHORIZATION);

        if (hasText(bearer) && bearer.toLowerCase().startsWith("bearer ")) {
            var token = bearer.substring(7).trim();
            log.debug("JwtFilter: token: {}", token);
            return token;
        }

        return null;
    }
}
