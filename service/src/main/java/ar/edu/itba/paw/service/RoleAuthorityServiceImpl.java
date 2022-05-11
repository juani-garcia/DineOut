package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.RoleAuthority;
import ar.edu.itba.paw.persistence.RoleAuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    private final RoleAuthorityDao roleAuthorityDao;

    @Autowired
    public RoleAuthorityServiceImpl(RoleAuthorityDao roleAuthorityDao) {
        this.roleAuthorityDao = roleAuthorityDao;
    }

    @Override
    public Optional<RoleAuthority> getByAuthorityId(long authorityId) {
        return roleAuthorityDao.getByAuthorityId(authorityId);
    }
}
