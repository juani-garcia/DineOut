package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.RoleToAuthority;
import ar.edu.itba.paw.persistence.RoleToAuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleToAuthorityServiceImpl implements RoleToAuthorityService {

    private final RoleToAuthorityDao roleToAuthorityDao;

    @Autowired
    public RoleToAuthorityServiceImpl(final RoleToAuthorityDao roleToAuthorityDao) {
        this.roleToAuthorityDao = roleToAuthorityDao;
    }

    @Override
    public List<RoleToAuthority> getByRoleId(long roleId) {
        return roleToAuthorityDao.getByRoleId(roleId);
    }
}
