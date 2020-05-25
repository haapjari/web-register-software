package com.marjakuusi.register.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Annotations: Turns on Web Security for this Application and tells framework to use this Class to configure security.
 */

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    /**
     * Method to block unauthenticated login requests to all pages except login page.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable cross-site request forgery.
                .requestCache().requestCache(new CustomRequestCache()) // Uses cache, so users are redirected right.
                .and().authorizeRequests() // Turns on authentication
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll() // Allows internal traffic from Vaadin

                .anyRequest().authenticated() // Allow any authenticated traffic

                .and().formLogin() // Enables login based form and permits unauthenticated access to it.
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL) // Confirms login page URLs
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL); // Configures logout URL
    }

    /**
     * Method to configure test users.
     * Defines single-user with credinteals "user" and "password"
     * @return user creds from inmemory user details manager
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Method that excludes Vaadin traffic from Spring Security Traffic
     * @param web traffic
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**");
    }

}
