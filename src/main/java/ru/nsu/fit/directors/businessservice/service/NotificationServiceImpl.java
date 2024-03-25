package ru.nsu.fit.directors.businessservice.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.businessservice.dto.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.businessservice.dto.response.NotificationDto;
import ru.nsu.fit.directors.businessservice.exceptions.BaseException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Notification;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;
import ru.nsu.fit.directors.businessservice.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final CompanyBranchRepository companyBranchRepository;
    private final NotificationRepository notificationRepository;
    private final SecurityService securityService;

    @Override
    public void handleOrderNotification(BusinessOrderNotificationEvent orderNotificationEvent) {
        Company company = companyBranchRepository.findById(orderNotificationEvent.businessId())
            .orElseThrow(() -> new BaseException("Establishment not found", "NotFoundException"));
        notificationRepository.save(
            new Notification()
                .setCompany(company)
                .setMessage(orderNotificationEvent.message())
                .setOrderId(orderNotificationEvent.orderId())
                .setWasReceived(false)
        );
    }

    @Override
    public List<NotificationDto> getNotifications() {
        BusinessUser businessUser = securityService.getLoggedInUser();
        List<Company> companies = new ArrayList<>(businessUser.getCompanies());
        companies.addAll(businessUser.getWorkerInCompanies());
        List<Notification> notifications = notificationRepository.findAllByCompanyInAndWasReceived(
            companies,
            false
        );
        notifications.forEach(notification -> notification.setWasReceived(true));
        notificationRepository.saveAll(notifications);
        return notifications.stream()
            .map(notification -> new NotificationDto(notification.getMessage()))
            .toList();
    }

    @Override
    public Flux<NotificationDto> getFluxNotifications() {
        return Flux.fromIterable(getNotifications());
    }
}
