package ar.edu.itba.paw.webapp.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagment().invalidSessionUrl("login").and().authorizeRequests().antMatchers("/login")
    }

}
