package com.marjakuusi.register.ui;

/**
 * @author jthaapas
 * @version 7.5.2020
 */

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

/**
 * This class is a Component, which acts as Form.
 */

// Form will use FormLayout
public class ContactForm extends FormLayout {

    /* Parameters */

    // Field Components for Component
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Contact.Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    /* -------------------------------------------------- */

    // This Class Binds Data Objects to UI
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    /* -------------------------------------------------- */

    /* Components are added to layout */
    public ContactForm(List<Company> companies) { // Company Object as parameter

        // CSS Class Name for Styling
        addClassName("contact-form");

        // Matches fields in Contact and ContactForm based on their names.
        binder.bindInstanceFields(this);

        // Sets list of companies as the items in the company combo box
        company.setItems(companies);

        // Tells the combo box to use the name of the company as the display value
        company.setItemLabelGenerator(Company::getName);

        // Populates the status dropdown with the values from the Contact.Status enum
        status.setItems(Contact.Status.values());
        add(
                firstName,
                lastName,
                email,
                company,
                status,
                createButtonsLayout() //
        );
    }

    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        // Save event calls validateAndSave method
        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    /**
     * Save event
     */
    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    /**
     * Method calls binder.setBean to bind values from the contact to the UI fields
     * @param contact
     */
    public void setContact(Contact contact) {
        binder.setBean(contact);
    }

    /**
     * Inner Class.
     */
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;

        // Common superclass for all events. Contains all contact that was edited or deleted.
        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    // Method uses Vaadin's event bus to register the custom event types.
    public <T extends ComponentEvent<?>> Registration addListener(Class<T>
        eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}









