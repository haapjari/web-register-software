package com.marjakuusi.register.ui.views.list;

import com.marjakuusi.register.backend.entity.Customer;
import com.marjakuusi.register.backend.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Product> companies;
    private Customer marcUsher;
    private Product product1;
    private Product product2;

    /* The @Before annotation adds dummy data that is used for testing. This method is executed before each @Test method */

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        product1 = new Product("Vaadin Ltd");
        product2 = new Product("IT Mill");
        companies.add(product1);
        companies.add(product2);

        marcUsher = new Customer();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(Customer.Status.Passive);
        marcUsher.setProduct(product2);
    }

    /**
     * Test method for populating data
     */
    @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies);
        form.setContact(marcUsher); // Validates that fields are populated correctly.

        // Uses standard JUnit assertEquals methods to compare values.
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc@usher.com", form.email.getValue());
        Assert.assertEquals(product2, form.company.getValue());
        Assert.assertEquals(Customer.Status.Passive, form.status.getValue());
    }

    /**
     * Test method for saving data
     */
    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies);
        Customer customer = new Customer();
        form.setContact(customer);

        // Populate data
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.company.setValue(product1);
        form.email.setValue("john@doe.com");
        form.status.setValue(Customer.Status.Active);

        // Test clicking save button and assert the values end up in the bean.
        AtomicReference<Customer> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> {

            /* ContactForm fires an event on save and the event data is needed for the test, an AtomicReference is used
             * to store the event data, without using a class field.
             */

            savedContactRef.set(e.getCustomer());
        });
        form.save.click();
        Customer savedCustomer = savedContactRef.get(); // Clicks save button

        Assert.assertEquals("John", savedCustomer.getFirstName());
        Assert.assertEquals("Doe", savedCustomer.getLastName());
        Assert.assertEquals("john@doe.com", savedCustomer.getEmail());
        Assert.assertEquals(product1, savedCustomer.getProduct());

        /* Once the data is available, you can use standard assertEquals calls to verify that the bean contains the expected values. */
        Assert.assertEquals(Customer.Status.Active, savedCustomer.getStatus());
    }
}
