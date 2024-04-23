package ru.nsu.fit.directors.businessservice.security.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {

    private String uuid;

    private String token;

    private Instant expireAt;
}
