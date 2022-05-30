package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class).setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public User create(String username, String password, String firstName, String lastName) {
        final User user = new User(username, password, firstName, lastName);
        em.persist(user);
        return user;
    }

}
