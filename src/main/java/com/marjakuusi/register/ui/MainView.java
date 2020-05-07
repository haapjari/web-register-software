package com.marjakuusi.register.ui;

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import com.marjakuusi.register.backend.service.CompanyService;
import com.marjakuusi.register.backend.service.ContactService;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

/**
 * @author jthaapas
 * @version 8.5.2020
 */

/**
 * Landing Page UI Class
 *
 * Defines some Vaadin components and gets data from backend.
 */

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    // Creates new ContactForm Component
    private final ContactForm form;

    // Creates grid object of Contact Objects
    Grid<Contact> grid = new Grid<>(Contact.class);

    // Creates TextField Object for filtering text
    TextField filterText = new TextField();

    // Creates an object of Contact Service, which is responsible for business logic
    private ContactService contactService;

    public MainView(ContactService contactService,
                    CompanyService companyService) {

        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureFilter(); // Method call for what filter should do

        // Initialize the form in the constructor.
        form = new ContactForm(companyService.findAll());

        // Creates HTML Div, that wraps Grid and form.
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        // Adds elements to Visible UI
        // add(filterText, content);
        add(filterText, content);
        updateList();
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

    /**
     * Method configures filter for filtering data.
     */
    private void configureFilter() {

        // Placeholder text
        filterText.setPlaceholder(("Filter by name ..."));

        // Clear button visible, so filter can be cleared
        filterText.setClearButtonVisible(true);

        // Creates delay for changes after typing.
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        /*
         * Lambda, listener for events.
         * Value Change triggers updateList() method call
         */
        filterText.addValueChangeListener((e -> updateList()));
    }

    /**
     * This method updates list of data, which is presented in grid.
     */
    private void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

}
