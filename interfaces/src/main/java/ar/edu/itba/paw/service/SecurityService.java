package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface SecurityService {

    String getCurrentUsername();

    Optional<User> getCurrentUser();

    boolean isLoggedIn();

    String validatePasswordResetToken(String token);
}
