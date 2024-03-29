package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.RoleAuthority;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.service.RoleAuthorityService;
import ar.edu.itba.paw.service.UserRoleService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Component
public class DineOutUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleAuthorityService roleAuthorityService;
    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");


    @Autowired
    public DineOutUserDetailsService(final UserService userService, final UserRoleService userRoleService, RoleAuthorityService roleAuthorityService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleAuthorityService = roleAuthorityService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user '" + username + "'"));
        final Collection<UserRole> userRoleList = userRoleService.getRolesOf(user.getId());

        final Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            // Paso por todos los roles del usuario y por cada uno me guardo su nombre y todos sus privilegios.
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName()));

            for (RoleAuthority roleAuthority : roleAuthorityService.getAuthoritiesOf(userRole.getId())) {
                authorities.add(new SimpleGrantedAuthority(roleAuthority.getAuthorityName()));
            }
        }

        if (userRoleList.isEmpty()) {
            UserRole userRole = userRoleService.getByRoleName("BASIC_USER")
                    .orElseThrow( () -> new IllegalStateException("ROLE_BASIC_USER missing from db"));
            authorities.add(new SimpleGrantedAuthority("ROLE_BASIC_USER"));
            user.addRole(userRole); // TODO: Check if works
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

}
