package ru.nsu.fit.directors.businessservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.businessservice.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
