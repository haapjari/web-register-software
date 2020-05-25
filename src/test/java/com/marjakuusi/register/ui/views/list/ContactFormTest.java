package com.marjakuusi.register.ui.views.list;

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Company> companies;
    private Contact marcUsher;
    private Company company1;
    private Company company2;

    /* The @Before annotation adds dummy data that is used for testing. This method is executed before each @Test method */

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company("Vaadin Ltd");
        company2 = new Company("IT Mill");
        companies.add(company1);
        companies.add(company2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(Contact.Status.NotContacted);
        marcUsher.setCompany(company2);
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
        Assert.assertEquals(company2, form.company.getValue());
        Assert.assertEquals(Contact.Status.NotContacted, form.status.getValue());
    }

    /**
     * Test method for saving data
     */
    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies);
        Contact contact = new Contact();
        form.setContact(contact);

        // Populate data
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.company.setValue(company1);
        form.email.setValue("john@doe.com");
        form.status.setValue(Contact.Status.Customer);

        // Test clicking save button and assert the values end up in the bean.
        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> {

            /* ContactForm fires an event on save and the event data is needed for the test, an AtomicReference is used
             * to store the event data, without using a class field.
             */

            savedContactRef.set(e.getContact());
        });
        form.save.click();
        Contact savedContact = savedContactRef.get(); // Clicks save button

        Assert.assertEquals("John", savedContact.getFirstName());
        Assert.assertEquals("Doe", savedContact.getLastName());
        Assert.assertEquals("john@doe.com", savedContact.getEmail());
        Assert.assertEquals(company1, savedContact.getCompany());

        /* Once the data is available, you can use standard assertEquals calls to verify that the bean contains the expected values. */
        Assert.assertEquals(Contact.Status.Customer, savedContact.getStatus());
    }
}
