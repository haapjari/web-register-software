package com.marjakuusi.register.ui.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * @author Jari Haapasaari
 * @version 30.5.2020
 * View Class. This class uses Vaadin Framework to display Graphical User Interface on Web Browser.
 */

/* Maps view to the "login" path. LoginView should use whole browser window, so we dont use MainLayout as parent. */

@Route("login")
@PageTitle("Login | Web Register Software")
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
        add(new H1("Web Register Software"), login);
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