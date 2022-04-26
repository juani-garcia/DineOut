package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
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

<<<<<<< HEAD
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserToRoleService userToRoleService;
    private final RoleToAuthorityService roleToAuthorityService;
    private final RoleAuthorityService roleAuthorityService;
=======
    private final UserService us;
    private final RestaurantService rs;
>>>>>>> 1956c4ff206082db71e326c62dc8b85646afc85e
    private final PasswordEncoder passwordEncoder;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");


    @Autowired
<<<<<<< HEAD
    public DineOutUserDetailsService(final UserService userService, final PasswordEncoder passwordEncoder, final UserRoleService userRoleService, final UserToRoleService userToRoleService, RoleToAuthorityService roleToAuthorityService, RoleAuthorityService roleAuthorityService) {
        this.userService = userService;
=======
    public DineOutUserDetailsService(final UserService us, final RestaurantService rs, final PasswordEncoder passwordEncoder) {
        this.us = us;
        this.rs = rs;
>>>>>>> 1956c4ff206082db71e326c62dc8b85646afc85e
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.userToRoleService = userToRoleService;
        this.roleToAuthorityService = roleToAuthorityService;
        this.roleAuthorityService = roleAuthorityService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.getByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("No user '" + username + "'") );

        final Collection<GrantedAuthority> authorities = new ArrayList<>();

        List<UserToRole> userToRoleList = userToRoleService.getByUserId(user.getId());

        for (UserToRole userToRole : userToRoleList) {
            // Paso por todos los roles del usuario y por cada uno me guardo su nombre y todos sus privilegios.
            Optional<UserRole> userRole = userRoleService.getByRoleId(userToRole.getRoleId());

            if (!userRole.isPresent()) throw new IllegalStateException("El rol del usuario es invalido");

            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.get().getRoleName()));

            for (RoleToAuthority userRoleToAuthority : roleToAuthorityService.getByRoleId(userRole.get().getId())) {
                Optional<RoleAuthority> roleAuthority = roleAuthorityService.getByAuthorityId(userRoleToAuthority.getAuthorityId());

                if (!roleAuthority.isPresent()) throw new IllegalStateException("El privielgio del rol del usuario es invalido");

                authorities.add(new SimpleGrantedAuthority(roleAuthority.get().getAuthorityName()));
            }
        }

        if (userToRoleList.isEmpty()) {
            Optional<UserRole> userRole = userRoleService.getByRoleName("BASIC_USER");
            if (!userRole.isPresent()) throw new IllegalStateException("ROLE_BASIC_USER missing from db");
            authorities.add(new SimpleGrantedAuthority("ROLE_BASIC_USER"));
            userToRoleService.create(user.getId(), userRole.get().getId());
        }

        // Migrate users with un-hashed password
        // Not necessary for us (didn't have users)
        /* String password = user.getPassword();
        if (! BCRYPT_PATTERN.matcher(password).matches()) {
            // TODO : Update user password in db!
            password = passwordEncoder.encode(password);
        } */

        // if (user. ...) roles.add(new SimpleGrantedAuthority("ROLE_EDITOR))

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

}
