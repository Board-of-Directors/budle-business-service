package ru.nsu.fit.directors.businessservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByRefreshTokenUuid(String tokenUuid);

    Optional<Session> findByUser(BusinessUser user);
}
