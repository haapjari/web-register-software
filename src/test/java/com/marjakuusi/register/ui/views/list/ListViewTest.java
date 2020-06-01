package com.marjakuusi.register.ui.views.list;

import com.marjakuusi.register.backend.entity.Customer;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jari Haapasaari
 * @version 1.6.2020
 * Test Class. Integration testing for ListView component.
 */
@RunWith(SpringRunner.class) // Takes care that Spring Boot is initialized before test, this allows @Autowired to be used in tests.
@SpringBootTest
public class ListViewTest {

    /* @Component annotation makes it possible to @Autowire it.
     * @Scope("prototype") ensures that every test gets run in fresh instance.
     */

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    @Autowired
    private ListView listView;

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     * Verifies that form logic works by asserting that the form is initially hidden and
     * selecting the first item in the grid and verifying that.
     */
    @Test
    public void formShownWhenContactSelected() {
        Grid<Customer> grid = listView.grid;
        Customer firstContact = getFirstItem(grid);

        CustomerForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstContact);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstContact, form.binder.getBean());
    }

    /**
     * @param grid Grid of Customer Objects.
     * @return Returns first item of Customer Objects.
     */
    private Customer getFirstItem(Grid<Customer> grid) {
        return( (ListDataProvider<Customer>) grid.getDataProvider()).getItems().iterator().next();
    }
}
