package com.marjakuusi.register.ui;

/**
 * @author jthaapas
 * @version 7.5.2020
 */

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * This class is a Component, which acts as Form.
 */

// Form will use FormLayout
public class ContactForm extends FormLayout {

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

    /* Components are added to layout */
    public ContactForm() {
        addClassName("contact-form");

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

        return new HorizontalLayout(save, delete, close);
    }
}
