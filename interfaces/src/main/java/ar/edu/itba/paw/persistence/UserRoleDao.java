package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.UserRole;

import java.util.Optional;

public interface UserRoleDao {
    Optional<UserRole> getByRoleId(final long roleId);

    Optional<UserRole> getByRoleName(String roleName);
}
