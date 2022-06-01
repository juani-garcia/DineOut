package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PasswordResetTokenHibernateDao implements PasswordResetTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<PasswordResetToken> getByUserId(long userId) {
        final TypedQuery<PasswordResetToken> query = em.
                createQuery("from PasswordResetToken as prt where prt.user.id = :userId order by prt.expiryDate ASC", PasswordResetToken.class);
        query.setParameter("userId", userId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public PasswordResetToken create(String token, User user, LocalDateTime expiryDate) {
        final PasswordResetToken prt = new PasswordResetToken(token, user, expiryDate);
        em.persist(prt);
        return prt;
    }

    @Override
    public Optional<PasswordResetToken> getByToken(String token) {
        final TypedQuery<PasswordResetToken> query = em.
                createQuery("from PasswordResetToken as prt where prt.token = :token order by prt.expiryDate ASC", PasswordResetToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

}
