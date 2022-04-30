package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface RoleAuthorityDao {
    Optional<RoleAuthority> getByAuthorityId(final long authorityId);
}
