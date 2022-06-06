package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.persistence.PasswordResetTokenDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Transactional
    @Override
    public PasswordResetToken create(String token, User user, LocalDateTime expiryDate) {
        return passwordResetTokenDao.create(token, user, expiryDate);
    }

    @Override
    public Optional<PasswordResetToken> getByUserId(long userId) {
        return passwordResetTokenDao.getByUserId(userId);
    }

    @Override
    public boolean hasValidToken(long userId) {
        Optional<PasswordResetToken> optionalPasswordResetToken = getByUserId(userId);
        return optionalPasswordResetToken.map(PasswordResetToken::isValid).orElse(false);
    }

    @Override
    public Optional<PasswordResetToken> getByToken(String token) {
        return passwordResetTokenDao.getByToken(token);
    }

    @Transactional
    @Override
    public void setUsed(String token) {
        PasswordResetToken prt = getByToken(token).orElseThrow(NotFoundException::new);
        prt.use();
    }
    
}
