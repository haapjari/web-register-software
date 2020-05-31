package com.marjakuusi.register.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jari Haapasaari
 * @version 31.5.2020
 * Cache to keep track of unauthenticated requests. Redirects the user to the page they were
 * trying to access before they were logged in.
 */
class CustomRequestCache extends HttpSessionRequestCache {

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }

}