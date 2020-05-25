package com.marjakuusi.register.ui;

import com.marjakuusi.register.ui.view.dashboard.DashboardView;
import com.marjakuusi.register.ui.view.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

/* CSS Import */

/* PWA Annnotation triggers Vaadin to Create necessary resources to deploy PWA (ServiceWorker and manifest file) */

@CssImport("./styles/shared-styles.css")
@PWA(
        name = "VaadinCRM",
        shortName = "VaadinCRM",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"})
public class MainLayout extends AppLayout { // App layout is Vaadin Layout with header and responsive drawer
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassName("logo");

        // DrawerToggle is a menu button that toggles visibility of a toolbar

        // Creates <a></a> HTML to binds that to logout.
        Anchor logout = new Anchor("logout", "Log out");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        // Calls header.expand(logo) to make the logo take up all the extra space in the layout. This pushes the logout button to the far right.
        header.expand(logo);



        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER); // Centers the component in the header along the vertical axis
        header.setWidth("100%");
        header.addClassName("header");


        addToNavbar(header); // Adds header layout to the apps layout's navbar
    }

    private void createDrawer() {

        // Creates RouterLink with text "List" and ListView Class as the destination view
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        // Sets highlight condition, avoids highlighting for partial route for same route matches.
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        // Wraps the link in a VerticalLayout and adds it to AppLayout's drawer
        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Dashboard", DashboardView.class)
        ));
    }


}
