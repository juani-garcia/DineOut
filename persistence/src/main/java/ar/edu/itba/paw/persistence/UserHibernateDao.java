package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao {

    private static final int PAGE_SIZE = 10;
    @PersistenceContext
    private EntityManager em;

    @Override
    public PagedQuery<User> getUsers(final int page) {
        String baseQuery = "FROM account";
        String idQuery = "SELECT id " + baseQuery + "LIMIT :limit OFFSET :offset";
        String countQuery = "SELECT COUNT(*) " + baseQuery;
        Query query = em.createNativeQuery(idQuery);
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page-1));
        final List<Long> ids = new ArrayList<>();
        for(Object o : query.getResultList()) {
            ids.add(((Integer) o).longValue());
        }

        query = em.createNativeQuery(countQuery);
        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

        final TypedQuery<User> users =
                em.createQuery("from User as u where u.id IN :ids", User.class);
        users.setParameter("ids", ids);

        return new PagedQuery<>(users.getResultList(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);
    }

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
    public User create(String username, String password, String firstName, String lastName, Locale locale) {
        final User user = new User(username, password, firstName, lastName, locale);
        em.persist(user);
        return user;
    }

}
