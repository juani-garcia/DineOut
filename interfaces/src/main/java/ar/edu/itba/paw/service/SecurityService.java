package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;

import java.util.Optional;

public interface SecurityService {

    String getCurrentUsername();

    Optional<User> getCurrentUser();

    boolean isLoggedIn();
}
