package ru.nsu.fit.directors.businessservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

import java.util.Optional;

public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {
    Optional<BusinessUser> findBusinessUserByLogin(String login);

    boolean existsBusinessUserByEmailOrPhoneNumber(String email, String phoneNumber);
}
