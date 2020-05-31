package com.marjakuusi.register.it;



import com.marjakuusi.register.it.elements.login.LoginViewElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jari Haapasaari
 * @version 31.5.2020
 * Test Class.
 */

/* TODO Documentation */


/* First integration test */

/* This test ensures that the user can log in */
public class LoginIT extends AbstractTest {

    /* ----------------------------------------------------------------------------------- */

    /* vaadin */

    public LoginIT() {
        super("");
    }

    /**
     *  Test method to validate that ensure that the user can login
     */
    @Test
    public void loginAsValidUserSucceeds() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("user", "password"));
    }

    /**
     * Test method to validate that user cannot login with invalid credinteals
     f*/
    @Test
    public void loginAsInvalidUserFails() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertFalse(loginView.login("user", "invalid"));
    }
}