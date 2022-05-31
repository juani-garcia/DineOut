package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRoleHibernateDao implements UserRoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<UserRole> getByRoleId(final long roleId) {
        return Optional.ofNullable(em.find(UserRole.class, roleId));
    }

    @Override
    public Optional<UserRole> getByRoleName(String roleName) {
        final TypedQuery<UserRole> query = em.createQuery("from UserRole as ur where ur.roleName = :roleName", UserRole.class);
        query.setParameter("roleName", roleName);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Collection<UserRole> getRolesOf(Long userId) {
        Optional<User> user = em.createQuery("SELECT u from User u JOIN FETCH u.roles WHERE u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList().stream().findFirst();
        return user.orElseThrow(NotFoundException::new).getRoles();
    }

}

