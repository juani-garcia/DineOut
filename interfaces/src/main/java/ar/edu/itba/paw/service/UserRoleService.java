package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.UserRole;

import java.util.Optional;

public interface UserRoleService {
    Optional<UserRole> getByRoleId(final long roleId);

    Optional<UserRole> getByRoleName(String roleName);

    public boolean hasRoleByUserId(final long userId, final String role);
}
