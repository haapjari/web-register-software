package com.marjakuusi.register.ui.views.dashboard;

import com.marjakuusi.register.backend.service.ProductService;
import com.marjakuusi.register.backend.service.CustomerService;
import com.marjakuusi.register.ui.MainLayout;
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

/* DashboardView is mapped to the "dashboard" path and uses MainLayout as parent layout */

/* PageTitle sets title for the page. */

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Web Register Software")
public class DashboardView extends VerticalLayout {

    private CustomerService customerService;
    private ProductService productService;

    /* Takes both services as parameters and saves them as fields */
    public DashboardView(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
        addClassName("dashboard-view");

        /* Centers contents of the layout */
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContactStats(), getCompaniesChart());
    }

    /**
     * Displays the number of contacts in the system
     * @return
     */
    private Component getContactStats() {
        Span stats = new Span(customerService.count() + " contacts");
        stats.addClassName("contact-stats");
        return stats;
    }

    /**
     * Method constructs the chart.
     * @return chart
     */
    private Chart getCompaniesChart() {

        // Creates new PIE Chart
        Chart chart = new Chart(ChartType.PIE);

        // Charts use a DataSeries for data
        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> companies = productService.getStats();
        companies.forEach((company, employees) ->
                dataSeries.add(new DataSeriesItem(company, employees))); // Adds a DataSeriesItem, containing the company name and number of employees, for each company.
        chart.getConfiguration().setSeries(dataSeries); // Sets the data series to the chart configuration.
        return chart;
    }
}