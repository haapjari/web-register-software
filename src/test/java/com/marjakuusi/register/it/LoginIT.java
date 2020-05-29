package com.marjakuusi.register.it;

/* First integration test */

/* This test ensures that the user can log in */

import com.marjakuusi.register.it.elements.login.LoginViewElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

public class LoginIT extends AbstractTest {
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