package ar.edu.itba.paw.persistence;

import java.util.List;

public interface UserToRoleDao {
    List<UserToRole> getByUserId(final long userId);

    UserToRole create(long userId, long roleId);
}
