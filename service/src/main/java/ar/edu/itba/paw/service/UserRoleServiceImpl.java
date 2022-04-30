package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.UserRole;
import ar.edu.itba.paw.persistence.UserToRole;
import ar.edu.itba.paw.persistence.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleDao userRoleDao;
    private final UserToRoleService userToRoleService;

    @Autowired
    public UserRoleServiceImpl(final UserRoleDao userRoleDao, UserToRoleService userToRoleService) {
        this.userRoleDao = userRoleDao;
        this.userToRoleService = userToRoleService;
    }

    @Override
    public Optional<UserRole> getByRoleId(final long roleId) {
        return userRoleDao.getByRoleId(roleId);
    }

    @Override
    public Optional<UserRole> getByRoleName(String roleName) {
        return userRoleDao.getByRoleName(roleName);
    }

    @Override
    public boolean hasRoleByUserId(final long userId, final String role) {
        List<UserToRole> userToRoleList = userToRoleService.getByUserId(userId);
        for (UserToRole userToRole : userToRoleList) {
            Optional<UserRole> userRole = this.getByRoleId(userToRole.getRoleId());
            if (!userRole.isPresent()) throw new IllegalStateException("Role: " + userToRole.getRoleId() + " does not exist");
            if (userRole.get().getRoleName().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
