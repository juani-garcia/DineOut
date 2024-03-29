package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Locale;

public class TestHelper {

    private TestHelper() {
        throw new AssertionError();
    }

    static User createUser(EntityManager em, String username, String password, String firstName, String lastName, Locale locale) {
        User user = new User(username, password, firstName, lastName, locale);
        em.persist(user);
        return user;
    }

    static int getRows(EntityManager em, String table) {
        return ((BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM " + table).getSingleResult()).intValue();
    }
}
