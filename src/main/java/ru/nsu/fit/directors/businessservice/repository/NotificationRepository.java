package ru.nsu.fit.directors.businessservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByCompanyInAndWasReceived(List<Company> companies, boolean wasReceived);
}
