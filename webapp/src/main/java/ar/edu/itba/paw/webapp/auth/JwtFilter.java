package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtFilter(UserService us, UserDetailsService uds, AuthenticationManager am, JwtUtils jwtu) {
        this.userService = us;
        this.authenticationManager = am;
        this.userDetailsService = uds;
        this.jwtUtils = jwtu;
    }

    private static final String REFRESH_CUSTOM_HEADER = "X-Refresh-Token",
                                SEPARATOR = " ";
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null) {
            LOGGER.debug("No authorization header, continuing...");
            chain.doFilter(request, response);
            return;
        }
        final String[] parts = header.split(SEPARATOR);
        AuthorizationMethod method;
        ContextProvider provider = null;
        try {
            LOGGER.debug("Getting AuthorizationMethod of value {}", parts[0]);
            method = AuthorizationMethod.valueOf(parts[0].toUpperCase());
            provider = new
                    ContextProvider(userService, authenticationManager, userDetailsService, request, response, jwtUtils);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Authorization error. Unsupported method {}", parts[0]);
            method = AuthorizationMethod.UNSUPPORTED;
        }

        LOGGER.debug("Authorizing with token {}", parts[1]);
        if(method.authorize(parts[1], provider)) {
            chain.doFilter(request, response);
        }
    }

    private static boolean setErrorStatus(HttpServletResponse response, int code) {
        response.setStatus(code);
        return false;
    }

    private static boolean unauthorized(HttpServletResponse response) {
        return setErrorStatus(response, Response.Status.UNAUTHORIZED.getStatusCode());
    }

    private enum AuthorizationMethod {
        BASIC {
            @Override
            boolean authorize(String token, ContextProvider context) {
                boolean error = false;
                String decoded = new String(Base64.getDecoder().decode(token));
                LOGGER.debug("Decoded credentials: {}", decoded);
                String[] credentials = decoded.split(":", 2);

                if(credentials.length < 2) {
                    LOGGER.debug("Bad credentials. No colon found. Credentials should be user:pass");
                    return unauthorized(context.response);
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);

                Optional<User> optionalUser = context.userService.getByUsername(credentials[0]);

                Authentication authenticate;
                try {
                    authenticate = context.authenticationManager.authenticate(authentication);
                } catch(BadCredentialsException e) {
                    return unauthorized(context.response);
                }

                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

                if (!optionalUser.isPresent()) {
                    LOGGER.error("Username {} not found", userDetails.getUsername());
                    return unauthorized(context.response);
                }

                User user = optionalUser.get();

                context.response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+context.jwtUtils.getToken(user));
                context.response.setHeader(REFRESH_CUSTOM_HEADER, "Bearer "+context.jwtUtils.getRefreshToken(user));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(context.request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return !error;
            }
        },
        BEARER {
            @Override
            boolean authorize(String token, ContextProvider context) {
                if(!context.jwtUtils.isValidToken(token)) {
                    return unauthorized(context.response);
                }

                String username = context.jwtUtils.getUsername(token);
                LOGGER.debug("Trying to authorize user {}", username);
                UserDetails userDetails;

                try {
                    userDetails = context.userDetailsService.loadUserByUsername(username);
                } catch(UsernameNotFoundException e) {
                    return unauthorized(context.response);
                }

                if(context.jwtUtils.isRefreshToken(token)) {
                    Optional<User> maybeUser = context.userService.getByUsername(userDetails.getUsername());

                    if(!maybeUser.isPresent()) {
                        return unauthorized(context.response); // Note: should not happen this far
                    }

                    context.response.setHeader(
                            HttpHeaders.AUTHORIZATION, "Bearer " + context.jwtUtils.getToken(maybeUser.get())
                    );
                }

                // TODO: Check granted authorities
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(context.request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
        },
        UNSUPPORTED {
            @Override
            boolean authorize(String token, ContextProvider context) {
                return unauthorized(context.response);
            }

        };
        private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationMethod.class);

        abstract boolean authorize(String token, ContextProvider context);

    }

    private static class ContextProvider {
        private final UserService userService;
        private final AuthenticationManager authenticationManager;
        private final UserDetailsService userDetailsService;
        private final HttpServletResponse response;
        private final HttpServletRequest request;
        private final JwtUtils jwtUtils;

        public ContextProvider(UserService userService, AuthenticationManager authenticationManager,
                               UserDetailsService userDetailsService, HttpServletRequest request,
                               HttpServletResponse response, JwtUtils jwtUtils) {
            this.userService = userService;
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.request = request;
            this.response = response;
            this.jwtUtils = jwtUtils;
        }
    }

}
