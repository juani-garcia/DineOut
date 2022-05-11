package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.persistence.PasswordResetTokenDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Override
    public PasswordResetToken create(String token, User user, LocalDateTime expiryDate, boolean isUsed) {
        return passwordResetTokenDao.create(token, user, expiryDate, isUsed);
    }

    @Override
    public Optional<PasswordResetToken> getByUserId(long userId) {
        return passwordResetTokenDao.getByUserId(userId);
    }

    @Override
    public boolean hasValidToken(long userId) {
        Optional<PasswordResetToken> optionalPasswordResetToken = getByUserId(userId);
        return optionalPasswordResetToken.map(passwordResetToken -> passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now()) && !passwordResetToken.isUsed()).orElse(false);
    }

    @Override
    public Optional<PasswordResetToken> getByToken(String token) {
        return passwordResetTokenDao.getByToken(token);
    }

    @Override
    public void setUsed(String token) {
        passwordResetTokenDao.setUsed(token);
    }
}
