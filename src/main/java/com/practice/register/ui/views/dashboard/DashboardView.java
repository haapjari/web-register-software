package com.practice.register.ui.views.dashboard;

import com.practice.register.backend.service.TypeService;
import com.practice.register.backend.service.CustomerService;
import com.practice.register.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

/**
 * @author haapjari
 * @version 31.5.2020
 * View Class. This class uses Vaadin Framework to display Graphical User Interface on Web Browser.
 */

/* DashboardView is mapped to the "dashboard" path and uses MainLayout as parent layout */

/* PageTitle sets title for the page. */

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Web Register Software")
public class DashboardView extends VerticalLayout {

    /* ----------------------------------------------------------------------------------- */

    /* attributes */

    private CustomerService customerService;
    private TypeService typeService;

    /* ----------------------------------------------------------------------------------- */

    /* constructors */

    /**
     * Constructs a Dashboard component.
     * @param customerService Service class, which handles the database access.
     * @param typeService Service class, which handles the database access.
     */
    public DashboardView(CustomerService customerService, TypeService typeService) {

        /* Takes both services as parameters and saves them as fields */
        this.customerService = customerService;
        this.typeService = typeService;

        // css class
        addClassName("dashboard-view");

        /* centers contents of the layout */
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Components to show stats, chart
        add(getCustomerStats(), getTypesChart());
    }

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     * @return Returns amount of customers in "customerService" object as Span
     */
    private Component getCustomerStats() {
        Span stats = new Span(customerService.count() + " customers");
        stats.addClassName("customer-stats");

        return stats;
    }

    /**
     * @return Pie-Chart of different types of customers.
     */
    private Chart getTypesChart() {

        // Creates new PIE Chart
        Chart chart = new Chart(ChartType.PIE);

        // Charts use a DataSeries for data
        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> types = typeService.getStats();
        types.forEach((type, customers) ->
                dataSeries.add(new DataSeriesItem(type, customers))); // Adds a DataSeriesItem, containing the company name and number of employees, for each company.
        chart.getConfiguration().setSeries(dataSeries); // Sets the data series to the chart configuration.
        return chart;
    }
}