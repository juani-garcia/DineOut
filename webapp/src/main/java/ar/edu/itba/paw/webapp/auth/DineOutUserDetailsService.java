package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class DineOutUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleToAuthorityService roleToAuthorityService;
    private final RoleAuthorityService roleAuthorityService;
    private final PasswordEncoder passwordEncoder;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");


    @Autowired
    public DineOutUserDetailsService(final UserService userService, final PasswordEncoder passwordEncoder, final UserRoleService userRoleService, RoleToAuthorityService roleToAuthorityService, RoleAuthorityService roleAuthorityService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.roleToAuthorityService = roleToAuthorityService;
        this.roleAuthorityService = roleAuthorityService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user '" + username + "'"));
        final Collection<UserRole> userRoleList = user.getRoles(); // TODO: Check if works

        final Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            // Paso por todos los roles del usuario y por cada uno me guardo su nombre y todos sus privilegios.
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName()));

            for (RoleToAuthority userRoleToAuthority : roleToAuthorityService.getByRoleId(userRole.getId())) {
                Optional<RoleAuthority> roleAuthority = roleAuthorityService.getByAuthorityId(userRoleToAuthority.getAuthority().getId());

                if (!roleAuthority.isPresent())
                    throw new IllegalStateException("El privilegio del rol del usuario es invalido");

                authorities.add(new SimpleGrantedAuthority(roleAuthority.get().getAuthorityName()));
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
