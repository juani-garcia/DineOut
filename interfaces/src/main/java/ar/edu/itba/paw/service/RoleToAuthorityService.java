package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.RoleToAuthority;

import java.util.List;

public interface RoleToAuthorityService {
    List<RoleToAuthority> getByRoleId(final long roleId);
}
