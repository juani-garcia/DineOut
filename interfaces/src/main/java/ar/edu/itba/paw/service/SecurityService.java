package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;

public interface SecurityService {

    public String getCurrentUsername();

    public User getCurrentUser();
}
