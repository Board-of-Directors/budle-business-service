package ru.nsu.fit.directors.businessservice.security.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ru.nsu.fit.directors.businessservice.configuration.SecurityProperties;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

public class AccessTokenClaims extends SMHTokenClaims {

    private SecurityProperties.KeyClaims keyClaims;

    public AccessTokenClaims(Claims claims) {
        super(claims);
    }

    public AccessTokenClaims(Claims claims, SecurityProperties.KeyClaims keyClaims) {
        this(claims);
        this.keyClaims = keyClaims;
    }

    public Claims build(String username, BusinessUser user, Long sessionId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(keyClaims.getUserId(), user.getId());
        claims.put(keyClaims.getSessionId(), sessionId);
        return claims;
    }
}
