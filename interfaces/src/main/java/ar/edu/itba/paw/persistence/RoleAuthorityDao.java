package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.RoleAuthority;

import java.util.Collection;
import java.util.Optional;

public interface RoleAuthorityDao {
    Optional<RoleAuthority> getByAuthorityId(final long authorityId);

    Collection<RoleAuthority> getAuthoritiesOf(Long roleId);
}
