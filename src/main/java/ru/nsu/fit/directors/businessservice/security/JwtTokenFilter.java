package ru.nsu.fit.directors.businessservice.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.fit.directors.businessservice.exceptions.TokenValidationException;
import ru.nsu.fit.directors.businessservice.exceptions.UnauthorizedException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/**/business/refresh");

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws IOException, ServletException {
        if (ignoredPaths.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwtTokenProvider.validateToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        } catch (TokenValidationException | UnauthorizedException exception) {
            onFailedAuthentication(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    protected void onFailedAuthentication(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Invalid token");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}
