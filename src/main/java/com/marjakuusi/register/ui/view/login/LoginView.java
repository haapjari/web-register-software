package com.marjakuusi.register.ui.view.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

/* Maps view to the "login" path. LoginView should use whole browser window, so we dont use MainLayout as parent. */

@Route("login")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();

        // Makes LoginView full size and centers it horizontally and vertically.
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Sets login form action "login" to post the login from Spring Security
        login.setAction("login");
        add(new H1("Vaadin CRM"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // inform the user about an authentication error
        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error"))
        {
            login.setError(true);
        }
    }
}