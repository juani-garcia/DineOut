package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.RoleAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class RoleAuthorityHibernateDao implements RoleAuthorityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RoleAuthority> getByAuthorityId(final long authorityId) {
        return Optional.ofNullable(em.find(RoleAuthority.class, authorityId));
    }

}

