package ar.edu.itba.paw.webapp.config;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

// Retrieved from: https://www.baeldung.com/spring-security-redirect-login
public class RefererRedirectionAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    public RefererRedirectionAuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
    }

}