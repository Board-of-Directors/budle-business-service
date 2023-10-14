package ru.nsu.fit.directors.businessservice.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

public interface SecurityService {

    BusinessUser getLoggedInUser();

    void autoLogin(String username, String password, HttpServletRequest request);

}
