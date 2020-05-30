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

/* Following annotations take care that SPring Boot is initialized before the tests are run and allow you to
 * use @Autowire in test
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    /* Test verifies that the form logic works by
     *      1. Asserting that the form is initially hidden.
     *      2. Selecting the first item in the grid and verifying that.
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
    private Customer getFirstItem(Grid<Customer> grid) {
        return( (ListDataProvider<Customer>) grid.getDataProvider()).getItems().iterator().next();
    }
}
