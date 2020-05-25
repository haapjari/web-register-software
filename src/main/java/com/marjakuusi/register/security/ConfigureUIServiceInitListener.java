package com.marjakuusi.register.security;

import com.marjakuusi.register.ui.view.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

/* Component annotation registers the listener, Vaadin will pick this up on start-up */

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    /**
     * In serviceInit, we listen for the initialization of the UI (the internal root component in Vaadin)
     * and then add a listener before every view transition.
     * @param event Event that is listened to
     */
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    /**
     * We redirect all request for login if user is not logged in.
     * @param event - Request
     */
    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }
}
