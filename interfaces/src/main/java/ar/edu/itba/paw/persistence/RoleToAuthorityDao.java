package ar.edu.itba.paw.persistence;

import java.util.List;

public interface RoleToAuthorityDao {
    List<RoleToAuthority> getByRoleId(final long roleId);
}
