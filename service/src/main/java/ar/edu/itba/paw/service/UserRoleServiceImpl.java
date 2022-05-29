package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.persistence.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(final UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Optional<UserRole> getByRoleId(final long roleId) {
        return userRoleDao.getByRoleId(roleId);
    }

    @Override
    public Optional<UserRole> getByRoleName(String roleName) {
        return userRoleDao.getByRoleName(roleName);
    }

}
