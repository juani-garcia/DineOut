package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.UserRole;

import java.util.Optional;

public interface UserRoleService {
    Optional<UserRole> getByRoleId(final long roleId);

    Optional<UserRole> getByRoleName(String roleName);
}
