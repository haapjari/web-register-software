package com.marjakuusi.register.security;

import com.marjakuusi.register.ui.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

/**
 * @author Jari Haapasaari
 * @version 31.5.2020
 * Class listens for the initialization of User Interface and adds then listener before every
 * view transition.
 */

/* Annotation registers the listener, Vaadin will pick this up on start-up */

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /* Spring Security restricts access to content based on paths. Vaadin applications
     * are single-page applications. This means that they do not trigger a full browser
     * refresh when you navigate between views, even though the path does change.
     * To secure a Vaadin application, Spring Security needs to be wired to
     * Vaadin navigation system.
     */

    /**
     * Listens for the initialization of the User Interface (the internal root component in Vaadin)
     * and then add a listener before every view transition.
     * @param event event that listens for initialization of the User Interface
     */
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    /**
     * Redirects all requests to LoginView if user is not already logged in.
     * @param event that is validated.
     */
    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }
}
