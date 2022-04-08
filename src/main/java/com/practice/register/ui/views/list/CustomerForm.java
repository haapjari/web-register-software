package com.practice.register.ui.views.list;

import com.practice.register.backend.entity.Customer;
import com.practice.register.backend.entity.Type;
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
 * @author haapjari
 * @version 30.5.2020
 * View Class. This class uses Vaadin Framework to display Graphical User Interface on Web Browser.
 */
public class CustomerForm extends FormLayout {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Customer.Status> status = new ComboBox<>("Status");
    ComboBox<Type> type = new ComboBox<>("Type");

    /* buttons */

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    /* This object binds data objects to user interface */

    Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    /* ----------------------------------------------------------------------------------- */

    /* components */

    /**
     * Constructor
     * @param types Initializes form component with parameter of different types.
     */
    public CustomerForm(List<Type> types) {

        // CSS
        addClassName("customer-form");

        // Binds fields in Customer and CustomerForm based on their names.
        binder.bindInstanceFields(this);

        // Sets list of types as the items in the type combo box
        type.setItems(types);

        // Tells the combo box to use the name of the type as the display value
        type.setItemLabelGenerator(Type::getName);

        // Populates the status dropdown with the values from the Customer.Status enum
        status.setItems(Customer.Status.values());
        add(
                firstName,
                lastName,
                email,
                type,
                status,
                createButtonsLayout() //
        );
    }

    /**
     * Component that creates buttons layout.
     * @return Buttons layout.
     */
    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        /* Some usability, keyboard functionality. */
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        // Save event calls validateAndSave method
        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    /* ----------------------------------------------------------------------------------- */

    /* events */

    /**
     * Validates the data before save event.
     */
    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    /**
     * Calls binder.setBean to bind values from the customer to the fields in UI.
     * @param customer Object which values are binded to the UI.
     */
    public void setContact(Customer customer) {
        binder.setBean(customer);
    }

    /* ----------------------------------------------------------------------------------- */

    /* inner class */

    /**
     * SuperClass for events in form.
     */
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private Customer customer;

        // Common superclass for all events. Contains all customer that was edited or deleted.

        /**
         * Common superclass for all form events. Contains data from customer that was edited or deleted.
         * @param source Which object will be shown on form.
         * @param customer Object that is on form.
         */
        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }

        public Customer getCustomer() {
            return customer;
        }
    }

    /**
     * Event: Save
     */
    public static class SaveEvent extends CustomerFormEvent {
        SaveEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    /**
     * Event: Delete
     */
    public static class DeleteEvent extends CustomerFormEvent {
        DeleteEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    /**
     * Event: Close
     */
    public static class CloseEvent extends CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    /**
     * This method uses Vaadins event bus to register custom event types.
     * @param eventType Custom event type.
     * @param listener Listener that event type is binded to.
     * @param <T> Type
     * @return Listener
     */
    // Method uses Vaadin's event bus to register the custom event types.
    public <T extends ComponentEvent<?>> Registration addListener(Class<T>
        eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}









