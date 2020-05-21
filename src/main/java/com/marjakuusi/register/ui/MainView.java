package com.marjakuusi.register.ui;

import com.marjakuusi.register.backend.entity.Company;
import com.marjakuusi.register.backend.entity.Contact;
import com.marjakuusi.register.backend.service.CompanyService;
import com.marjakuusi.register.backend.service.ContactService;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        // configureFilter(); // Method call for what filter should do

        // Initialize the form in the constructor.
        form = new ContactForm(companyService.findAll());

        // Listeners to forms.
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        // Creates HTML Div, that wraps Grid and form.
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        // Adds elements to Visible UI
        // add(filterText, content);
        add(getToolbar(), content);
        updateList();

        /* Clears old values, hides the form and removes "editing" CSS class */
        closeEditor();
    }

    /**
     * Method uses contactService to save the contact in the event to the data
     * base. Then method updates the list and closes the editor.
     * @param event event that is handled
     */
    private void saveContact(ContactForm.SaveEvent event) {
        contactService.save(event.getContact());
        updateList();
        closeEditor();
    }

    /**
     * Method uses contactService to delete contact from the database, then
     * method updates list and clsoes the editor.
     * @param event event that is handled
     */
    private void deleteContact(ContactForm.DeleteEvent event) {
        contactService.delete(event.getContact());
        updateList();
        closeEditor();
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

        /*
         * Adds listener to the grid, we only want Contact so we use
         */
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    /**
     * Sets selected contact in the ContactForm and hides or shows the form,
     * depending on the selection. IT also sets the "editing" CSS class name
     * when editing.
     */
    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
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

    /**
     * Call at the end of the constructor Sets,
     * clearing out old values and hides the form.
     */
    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    /**
     * Creates layout, that wraps textfield and button, renames configureFilter
     * method to configureToolbar and replace the contents as follows.
     * @return Returns the HorizontalLayout Component
     */
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /**
     * Deleselectes the grid and creates new Contact Object and passes that
     * to editContact method.
     */
    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

}
