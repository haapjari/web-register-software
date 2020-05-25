package com.marjakuusi.register.ui.views.dashboard;

import com.marjakuusi.register.backend.service.CompanyService;
import com.marjakuusi.register.backend.service.ContactService;
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
@PageTitle("Dashboard | Vaadin CRM")
public class DashboardView extends VerticalLayout {

    private ContactService contactService;
    private CompanyService companyService;

    /* Takes both services as parameters and saves them as fields */
    public DashboardView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;
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
        Span stats = new Span(contactService.count() + " contacts");
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
        Map<String, Integer> companies = companyService.getStats();
        companies.forEach((company, employees) ->
                dataSeries.add(new DataSeriesItem(company, employees))); // Adds a DataSeriesItem, containing the company name and number of employees, for each company.
        chart.getConfiguration().setSeries(dataSeries); // Sets the data series to the chart configuration.
        return chart;
    }
}