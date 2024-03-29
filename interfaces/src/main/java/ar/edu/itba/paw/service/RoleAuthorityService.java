package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.RoleAuthority;

import java.util.Collection;
import java.util.Optional;

public interface RoleAuthorityService {
    Optional<RoleAuthority> getByAuthorityId(final long authorityId);

    Collection<RoleAuthority> getAuthoritiesOf(Long roleId);
}
