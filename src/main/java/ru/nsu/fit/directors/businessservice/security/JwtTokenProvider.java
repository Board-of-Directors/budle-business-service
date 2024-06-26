package ru.nsu.fit.directors.businessservice.security;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.configuration.SecurityProperties;
import ru.nsu.fit.directors.businessservice.exceptions.TokenValidationException;
import ru.nsu.fit.directors.businessservice.exceptions.UnauthorizedException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.security.claims.AccessTokenClaims;
import ru.nsu.fit.directors.businessservice.security.claims.RefreshTokenClaims;
import ru.nsu.fit.directors.businessservice.security.claims.SMHTokenClaims;
import ru.nsu.fit.directors.businessservice.security.model.RefreshToken;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private String secret;

    public static final String SCHEMA_ALGORITHM = "Bearer ";

    private final SecurityProperties securityProperties;

    private AccessTokenClaims accessTokenClaims;

    public String createAccessToken(String username, BusinessUser user, Long sessionId) {
        var claims = accessTokenClaims.build(username, user, sessionId);
        var validityDuration = securityProperties.getRegular().getAccessTokenValiditySeconds();

        return createToken(claims, validityDuration);
    }

    public RefreshToken createRefreshToken(String username) {
        String tokenUuid = UUID.randomUUID().toString();

        var validityDuration = securityProperties.getRegular().getRefreshTokenValidityDays();
        var claims = RefreshTokenClaims.build(username, tokenUuid);

        return RefreshToken.builder()
            .expireAt(Instant.now().plusSeconds(validityDuration.toDays()))
            .uuid(tokenUuid)
            .token(createToken(claims, validityDuration))
            .build();
    }

    public void validateToken(String bearerToken) throws TokenValidationException {
        Optional.ofNullable(bearerToken)
            .filter(token -> token.startsWith(SCHEMA_ALGORITHM))
            .map(token -> token.substring(SCHEMA_ALGORITHM.length()))
            .flatMap(token -> validateToken(token, AccessTokenClaims.class));
    }

    @Nonnull
    public <T extends SMHTokenClaims> Optional<T> validateToken(String token, Class<T> clazz)
        throws TokenValidationException {
        try {
            Constructor<T> cons = clazz.getConstructor(Claims.class);
            return Optional.of(cons.newInstance(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()));
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("token expired: {}", Optional.ofNullable(expiredJwtException.getClaims())
                .map(Claims::getSubject).orElse(null));
            throw new UnauthorizedException(expiredJwtException.getMessage());
        } catch (Exception exception) {
            throw new TokenValidationException(exception.getMessage());
        }
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(securityProperties.getSecret().getBytes());
        accessTokenClaims = new AccessTokenClaims(null, securityProperties.getKeyClaims());
    }

    private String createToken(Claims claims, Duration duration) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + duration.toMillis());

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }
}
