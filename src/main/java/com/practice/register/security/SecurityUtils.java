package com.practice.register.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author haapjari
 * @version 31.5.2020
 * Utility Methods.
 */
public final class SecurityUtils {

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    private SecurityUtils() {
        // Util methods only
    }

    /**
     * Determines if this request is internal to Vaadin Framework
     * @param request Request to be proceed
     * @return Excepted behaviour
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    /**
     * tests if user is already authenticated and logged in
     * @return boolean value, if user is already authenticated and logged in
     */
    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }
}
