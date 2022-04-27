package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.UserToRole;

import java.util.List;
import java.util.Optional;

public interface UserToRoleDao {
    List<UserToRole> getByUserId(final long userId);

    UserToRole create(long userId, long roleId);
}
