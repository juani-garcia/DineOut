package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.RoleToAuthority;

import java.util.List;

public interface RoleToAuthorityDao {
    List<RoleToAuthority> getByRoleId(final long roleId);
}
