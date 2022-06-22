package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.webapp.auth.DineOutUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.concurrent.TimeUnit;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private DineOutUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_RESTAURANT \n ROLE_ADMIN > ROLE_DINER \n ROLE_DINER > ROLE_BASIC_USER \n ROLE_RESTAURANT > ROLE_BASIC_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public AuthenticationSuccessHandler success() {
        return new RefererRedirectionAuthenticationSuccessHandler("/");
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .invalidSessionUrl("/")
                .and().authorizeRequests()
                .antMatchers("/", "/restaurants").permitAll()
                .antMatchers("/login", "/register").anonymous()
                .antMatchers("/restaurant/item/**").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/restaurant/section/**").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/restaurant/register").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/restaurant/edit").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/restaurant").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/restaurant/**/view").permitAll()
                .antMatchers("/restaurant_picker").permitAll()
                .antMatchers("/forgot_my_password").permitAll()
                .antMatchers("/reset_password").permitAll()
                .antMatchers("/change_password").permitAll()
                .antMatchers("/save_password").permitAll()
                .antMatchers("/reserve/**").hasRole(Role.DINER.getRoleName())
                .antMatchers("/create/**").hasRole(Role.DINER.getRoleName())
                .antMatchers("/reservation/**/confirm").hasRole(Role.RESTAURANT.getRoleName())
                .antMatchers("/diner/**").hasRole(Role.DINER.getRoleName())
                .anyRequest().authenticated()
                .and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .loginPage("/login")
                .successHandler(success())
                .and().rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService)
                .key(env.getProperty("webauthconfig.rememberme.key"))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/image/**");
    }

}
