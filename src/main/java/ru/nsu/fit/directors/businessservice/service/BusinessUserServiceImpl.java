package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.exceptions.InvalidCredentialsException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import javax.annotation.ParametersAreNonnullByDefault;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class BusinessUserServiceImpl implements BusinessUserService, UserDetailsService {
    private final BusinessUserRepository businessUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public void registerBusinessUser(BusinessUserRegisterRequest businessUserRegisterRequest) {
        String password = RandomStringUtils.random(12, true, true);
        BusinessUser businessUser = toBusinessUser(businessUserRegisterRequest, password);
        businessUserRepository.save(businessUser);
        sendMailNotification(businessUser, password);
    }

    private void sendMailNotification(BusinessUser user, String password) {
        String text = String.join("\n",
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

    private BusinessUser toBusinessUser(BusinessUserRegisterRequest businessUserRegisterRequest, String password) {
        String[] names = businessUserRegisterRequest.name().split(" ");
        String login = "admin";
        return new BusinessUser()
            .setMiddleName(names[0])
            .setFirstName(names[1])
            .setLastName(names[2])
            .setEmail(businessUserRegisterRequest.email())
            .setPhoneNumber(businessUserRegisterRequest.phoneNumber())
            .setPassword(passwordEncoder.encode(password))
            .setLogin(login);
    }

    @Override
    public void loginBusinessUser(BusinessUserLoginRequest businessUserLoginRequest) {
        businessUserRepository.findBusinessUserByLogin(businessUserLoginRequest.login())
            .orElseThrow(InvalidCredentialsException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return businessUserRepository.findBusinessUserByLogin(username)
            .orElseThrow(InvalidCredentialsException::new);
    }
}
