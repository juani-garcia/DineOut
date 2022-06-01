package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.RoleAuthority;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

@Repository
public class RoleAuthorityHibernateDao implements RoleAuthorityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RoleAuthority> getByAuthorityId(final long authorityId) {
        return Optional.ofNullable(em.find(RoleAuthority.class, authorityId));
    }

    @Override
    public Collection<RoleAuthority> getAuthoritiesOf(Long roleId) {
        Optional<UserRole> userRole = em.createQuery("SELECT r from UserRole r JOIN FETCH r.authorities WHERE r.id = :roleId", UserRole.class)
                .setParameter("roleId", roleId)
                .getResultList().stream().findFirst();
        return userRole.orElseThrow(NotFoundException::new).getAuthorities();
    }

}

