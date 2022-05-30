package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.RoleToAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleToAuthorityHibernateDao implements RoleToAuthorityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RoleToAuthority> getByRoleId(final long roleId) {
        final TypedQuery<RoleToAuthority> query = em.createQuery("from RoleToAuthority as rta where rta.userRole.id = :roleId", RoleToAuthority.class);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }

}

