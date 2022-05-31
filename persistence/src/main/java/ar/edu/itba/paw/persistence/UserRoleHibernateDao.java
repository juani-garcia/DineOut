package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

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

}

