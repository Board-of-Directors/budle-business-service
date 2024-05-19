package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class MailNotificationServiceImpl implements MailNotificationService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationNotification(BusinessUser user, String password) {
        String text = String.join(
            "\n",
            "Добрый день, %s %s!\n".formatted(user.getFirstName(), user.getLastName()),
            "Мы поздравляем вас с тем, что вы успешно зарегистрировались на нашем сервисе! Высылаем вам ваши логин и пароль.",
            "Логин: %s".formatted(user.getLogin()),
            "Пароль: %s".formatted(password),
            "\nС уважением и заботой, команда Budle."
        );
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Budle");
            message.setTo(user.getEmail());
            message.setSubject("Вы успешно зарегистрировались!");
            message.setText(text);
            javaMailSender.send(message);
        }).start();
    }
}
