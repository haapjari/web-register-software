package com.practice.register.it;



import com.practice.register.it.elements.login.LoginViewElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author haapjari
 * @version 1.6.2020
 * Test Class. End to end testing, ensuring that user can log in.
 */
public class LoginIT extends AbstractTest {

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    public LoginIT() {
        super("");
    }

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     *  Validates that that the user can login with right credentials.
     */
    @Test
    public void loginAsValidUserSucceeds() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("user", "password"));
    }

    /**
     * Validate that user cannot login with invalid credentials.
     */
    @Test
    public void loginAsInvalidUserFails() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertFalse(loginView.login("user", "invalid"));
    }
}