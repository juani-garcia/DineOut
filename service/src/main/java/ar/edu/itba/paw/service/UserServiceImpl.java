package ar.edu.itba.paw.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public String getUsername() {
        return "PAW";
    }
}
