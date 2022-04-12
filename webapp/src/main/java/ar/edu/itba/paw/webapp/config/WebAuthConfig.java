package ar.edu.itba.paw.webapp.config;

public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagment().invalidSessionUrl("login").and().authorizeRequests().antMatchers("/login")
    }

}
