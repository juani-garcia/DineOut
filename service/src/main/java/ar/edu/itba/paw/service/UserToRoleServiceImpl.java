package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.UserToRole;
import ar.edu.itba.paw.persistence.UserToRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {

    private final UserToRoleDao userToRoleDao;

    @Autowired
    public UserToRoleServiceImpl(final UserToRoleDao userToRoleDao) {
        this.userToRoleDao = userToRoleDao;
    }

    @Override
    public List<UserToRole> getByUserId(long userId) {
        return userToRoleDao.getByUserId(userId);
    }

    @Override
    public UserToRole create(long userId, long roleId) {
        return userToRoleDao.create(userId, roleId);
    }
}
