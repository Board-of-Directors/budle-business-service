package ru.nsu.fit.directors.businessservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;

@Repository
public interface AvailableOptionRepository extends JpaRepository<AvailableOption, Long> {
    Optional<AvailableOption> findByBusinessUserAndCompanyAndOption(
        BusinessUser businessUser,
        Company company,
        Option option
    );
}
