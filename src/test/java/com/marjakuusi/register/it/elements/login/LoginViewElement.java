package com.marjakuusi.register.it.elements.login;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.annotations.Attribute;

/**
 * @author Jari Haapasaari
 * @version 31.5.2020
 * Test Class.
 */

/* TODO Documentation */

/* This annotation selects this depenceny: com.vaadin.testbench.annotations.Attribute */
/* Adding the @Attribute(name = "class", contains = "login-view") annotation allows you to find the LoginViewElement */
@Attribute(name = "class", contains = "login-view")
public class LoginViewElement extends VerticalLayoutElement {

    /* ----------------------------------------------------------------------------------- */

    /* vaadin */

    public boolean login(String username, String password) {
        LoginFormElement form = $(LoginFormElement.class).first();
        form.getUsernameField().setValue(username);
        form.getPasswordField().setValue(password);
        form.getSubmitButton().click();

        // Return true if we end up on another page
        return !$(LoginViewElement.class).onPage().exists();
    }

}
