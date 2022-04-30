package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.RoleToAuthority;

import java.util.List;

public interface RoleToAuthorityService {
    List<RoleToAuthority> getByRoleId(final long roleId);
}
