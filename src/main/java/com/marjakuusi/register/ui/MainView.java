package com.marjakuusi.register.ui;

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import com.marjakuusi.register.backend.service.ContactService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jthaapas
 * @version 7.5.2020
 */

/**
 * Landing Page UI Class
 *
 * Defines some Vaadin components and gets data from backend.
 */

@Route("")
public class MainView extends VerticalLayout {

    // Creates an object of Contact Service, which is responsible for business logic
    private ContactService contactService;
    private Grid<Contact> grid = new Grid<>(Contact.class);

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    /**
     * This method updates list of data, which is presented in grid.
     */
    private void updateList() {
        grid.setItems(contactService.findAll());
    }

    /**
     * Configures Grid CSS Classname and Titles
     */
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        // Removes the default column definition
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");

        /*
         * Method adds customn column
         *
         * addColumn gets a contact parameter, company name or dash.
         */
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        /*
         * Turns on automatic sizing, this has to be done invidually to each column.
         *
         * This piece of code loops through every column.
         */
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
