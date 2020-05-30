package com.marjakuusi.register.ui.views.list;

import com.marjakuusi.register.backend.entity.Customer;
import com.marjakuusi.register.backend.entity.Product;
import com.marjakuusi.register.backend.service.ProductService;
import com.marjakuusi.register.backend.service.CustomerService;

import com.marjakuusi.register.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author jthaapas
 * @version 8.5.2020
 */

/**
 * Landing Page UI Class
 *
 * Defines some Vaadin components and gets data from backend.
 */

/* ListView matches empty path, but uses MainLayout as its parent, this is why we can also remove CssImport */

/* Component annotation makes it possible to autowire it, Scope ensures that every test runs on fresh instance */

@Component
@Scope("prototype")
@Route(value="", layout = MainLayout.class)
@PageTitle("Customers | Web Register Software")
public class ListView extends VerticalLayout {

    // Creates new ContactForm Component
    ContactForm form;

    // Creates grid object of Customer Objects
    Grid<Customer> grid = new Grid<>(Customer.class);

    // Creates TextField Object for filtering text
    TextField filterText = new TextField();

    // Creates an object of Customer Service, which is responsible for business logic
    private CustomerService customerService;

    public ListView(CustomerService customerService,
                    ProductService productService) {

        this.customerService = customerService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        // configureFilter(); // Method call for what filter should do

        // Initialize the form in the constructor.
        form = new ContactForm(productService.findAll());

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
        customerService.save(event.getCustomer());
        updateList();
        closeEditor();
    }

    /**
     * Method uses contactService to delete contact from the database, then
     * method updates list and clsoes the editor.
     * @param event event that is handled
     */
    private void deleteContact(ContactForm.DeleteEvent event) {
        customerService.delete(event.getCustomer());
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
        grid.removeColumnByKey("product");
        grid.setColumns("firstName", "lastName", "email", "status");

        /*
         * Method adds customn column
         *
         * addColumn gets a contact parameter, company name or dash.
         */
        grid.addColumn(customer -> {
            Product product = customer.getProduct();
            return product == null ? "-" : product.getName();
        }).setHeader("Product");

        /*
         * Turns on automatic sizing, this has to be done invidually to each column.
         *
         * This piece of code loops through every column.
         */
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        /*
         * Adds listener to the grid, we only want Customer so we use
         */
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    /**
     * Sets selected customer in the ContactForm and hides or shows the form,
     * depending on the selection. IT also sets the "editing" CSS class name
     * when editing.
     */
    public void editContact(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            form.setContact(customer);
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
        grid.setItems(customerService.findAll(filterText.getValue()));
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
     * Deleselectes the grid and creates new Customer Object and passes that
     * to editContact method.
     */
    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Customer());
    }

}
