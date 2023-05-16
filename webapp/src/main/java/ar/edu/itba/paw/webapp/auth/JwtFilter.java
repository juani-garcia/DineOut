package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

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
            provider = new ContextProvider(userService, authenticationManager, userDetailsService);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Authorization error. Unsupported method {}", parts[0]);
            method = AuthorizationMethod.UNSUPPORTED;
        }

        if(userService == null || authenticationManager == null || userDetailsService == null) {
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("******************************************************************************************");
            LOGGER.debug("Autowired components are null");
        } else {
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("Autowired components are NOTnot null");
        }

        LOGGER.debug("Authorizing with token {}", parts[1]);
        if(method.authorize(parts[1], response, provider)) {
            chain.doFilter(request, response);
        }
    }

    private enum AuthorizationMethod {

        BASIC {
            @Override
            boolean authorize(String token, HttpServletResponse response, ContextProvider provider) {
                boolean error = false;
                String decoded = new String(Base64.getDecoder().decode(token));
                LOGGER.debug("Decoded credentials: {}", decoded);
                String[] credentials = decoded.split(":", 2);

                if(credentials.length < 2) {
                    LOGGER.error("Bad credentials. No colon found. Credentials should be user:pass");
                    return false;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);

                Optional<User> optionalUser = provider.userService.getByUsername(credentials[0]);
                Authentication authenticate = provider.authenticationManager.authenticate(authentication);
                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

                if (!optionalUser.isPresent()) {
                    LOGGER.error("Username {} not found", userDetails.getUsername());
                    return false;
                }

                User user = optionalUser.get();

                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + user.getUsername());
                response.setHeader("X-Refresh", "Bearer " + user.getPassword());

                // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // SecurityContextHolder.getContext().setAuthentication(authentication);
                return !error;
            }
        },
        DIGEST {
            @Override
            boolean authorize(String token, HttpServletResponse response, ContextProvider provider) {
                return false;
            }
        },
        BEARER {
            @Override
            boolean authorize(String token, HttpServletResponse response, ContextProvider provider) {
                return false;
            }
        },
        UNSUPPORTED {
            @Override
            boolean authorize(String token, HttpServletResponse response, ContextProvider provider) {
                response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
                return false;
            }
        };

        private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationMethod.class);

        abstract boolean authorize(String token, HttpServletResponse response, ContextProvider provider);

    }

    private static class ContextProvider {
        private UserService userService;
        private AuthenticationManager authenticationManager;
        private UserDetailsService userDetailsService;

        public ContextProvider(UserService userService, AuthenticationManager authenticationManager,
                               UserDetailsService userDetailsService) {
            this.userService = userService;
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
        }
    }

}
