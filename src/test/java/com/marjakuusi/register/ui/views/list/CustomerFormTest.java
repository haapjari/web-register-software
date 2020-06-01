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
 * @author Jari Haapasaari
 * @version 1.6.2020
 * Test Class for Customer Form. Unit testing.
 */

public class CustomerFormTest {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private List<Type> differentTypes;
    private Customer akuAnkka;
    private Type type1;
    private Type type2;

    /* ----------------------------------------------------------------------------------- */

    /* test logic */

    /**
     * @Before annotation is executed before @Test
     * Setups data for testing purposes.
     */
    @Before
    public void setupData() {
        differentTypes = new ArrayList<>();
        type1 = new Type("Business");
        type2 = new Type("Consumer");
        differentTypes.add(type1);
        differentTypes.add(type2);

        akuAnkka = new Customer();
        akuAnkka.setFirstName("Aku");
        akuAnkka.setLastName("Ankka");
        akuAnkka.setEmail("aku@jyu.com");
        akuAnkka.setStatus(Customer.Status.POC);
        akuAnkka.setType(type2);
    }

    /**
     * Populates data to CustomerForm. Uses JUnit.
     */
    @Test
    public void formFieldsPopulated() {
        CustomerForm form = new CustomerForm(differentTypes);
        form.setContact(akuAnkka); // Validates that fields are populated correctly.

        // Uses standard JUnit assertEquals methods to compare values.
        Assert.assertEquals("Aku", form.firstName.getValue());
        Assert.assertEquals("Ankka", form.lastName.getValue());
        Assert.assertEquals("aku@jyu.com", form.email.getValue());
        Assert.assertEquals(type2, form.type.getValue());
        Assert.assertEquals(Customer.Status.POC, form.status.getValue());
    }

    /**
     * Uses save event and validates that correct field data ends up in the bean.
     */
    @Test
    public void saveEventHasCorrectValues() {
        CustomerForm form = new CustomerForm(differentTypes);
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
