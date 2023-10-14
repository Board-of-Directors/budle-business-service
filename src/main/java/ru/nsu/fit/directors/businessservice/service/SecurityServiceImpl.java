package ru.nsu.fit.directors.businessservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.exceptions.UserNotLoggedInException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final BusinessUserRepository businessUserRepository;

    @Override
    public BusinessUser getLoggedInUser() {
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return businessUserRepository.findBusinessUserByLogin(login)
            .orElseThrow(UserNotLoggedInException::new);
    }

    @Override
    public void autoLogin(String username, String password, HttpServletRequest request) {
        log.info("Auto login for {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User with this username does not exist");
        }
        log.info("Auto login success for {}", userDetails);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
            );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
    }
}
