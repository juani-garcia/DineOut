package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.UserToRole;

import java.util.List;

public interface UserToRoleService {
    List<UserToRole> getByUserId(final long userId);

    UserToRole create(long userId, long roleId);
}
