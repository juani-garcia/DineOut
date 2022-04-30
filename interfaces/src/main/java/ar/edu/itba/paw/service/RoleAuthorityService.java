package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.RoleAuthority;

import java.util.Optional;

public interface RoleAuthorityService {
    Optional<RoleAuthority> getByAuthorityId(final long authorityId);
}
