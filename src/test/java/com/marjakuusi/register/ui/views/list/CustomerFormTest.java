package com.marjakuusi.register.ui.views.list;

import com.marjakuusi.register.backend.entity.Customer;
import com.marjakuusi.register.backend.entity.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jthaapas
 * @version 30.5.2020
 * TODO Documentation
 */


public class CustomerFormTest {
    private List<Type> companies;
    private Customer marcUsher;
    private Type type1;
    private Type type2;

    /* The @Before annotation adds dummy data that is used for testing. This method is executed before each @Test method */

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        type1 = new Type("Vaadin Ltd");
        type2 = new Type("IT Mill");
        companies.add(type1);
        companies.add(type2);

        marcUsher = new Customer();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(Customer.Status.POC);
        marcUsher.setType(type2);
    }

    /**
     * Test method for populating data
     */
    @Test
    public void formFieldsPopulated() {
        CustomerForm form = new CustomerForm(companies);
        form.setContact(marcUsher); // Validates that fields are populated correctly.

        // Uses standard JUnit assertEquals methods to compare values.
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc@usher.com", form.email.getValue());
        Assert.assertEquals(type2, form.type.getValue());
        Assert.assertEquals(Customer.Status.POC, form.status.getValue());
    }

    /**
     * Test method for saving data
     */
    @Test
    public void saveEventHasCorrectValues() {
        CustomerForm form = new CustomerForm(companies);
        Customer customer = new Customer();
        form.setContact(customer);

        // Populate data
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.type.setValue(type1);
        form.email.setValue("john@doe.com");
        form.status.setValue(Customer.Status.CEO);

        // Test clicking save button and assert the values end up in the bean.
        AtomicReference<Customer> savedContactRef = new AtomicReference<>(null);
        form.addListener(CustomerForm.SaveEvent.class, e -> {

            /* CustomerForm fires an event on save and the event data is needed for the test, an AtomicReference is used
             * to store the event data, without using a class field.
             */

            savedContactRef.set(e.getCustomer());
        });
        form.save.click();
        Customer savedCustomer = savedContactRef.get(); // Clicks save button

        Assert.assertEquals("John", savedCustomer.getFirstName());
        Assert.assertEquals("Doe", savedCustomer.getLastName());
        Assert.assertEquals("john@doe.com", savedCustomer.getEmail());
        Assert.assertEquals(type1, savedCustomer.getType());

        /* Once the data is available, you can use standard assertEquals calls to verify that the bean contains the expected values. */
        Assert.assertEquals(Customer.Status.CEO, savedCustomer.getStatus());
    }
}
