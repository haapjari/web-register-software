package com.marjakuusi.register.it;

import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.parallel.ParallelTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author Jari Haapasaari
 * @version 1.6.2020
 * Abstract Test Class. End-to-end tests are used to test the entire application.
 * End-to-end tests are executed in a browser window, controlled by a web driver and run on the server where the application is deployed.
 * Setup:
 *     1. Configure Maven to start the Spring Boot server before running tests and to stop it afterwards.
 *     2. Make sure you have Chrome installed and install a web driver manager that will download the needed web driver.
 *     3. Create a base test class that starts a browser and opens the application URL.
 */
public abstract class AbstractTest extends ParallelTest {

    /**
     * Starts Web Driver Manager
     */
    @BeforeClass
    public static void setupClass() {

        // Starts Chrome Web Driver Manager
        WebDriverManager.chromedriver().setup(); //
    }

    /**
     *  Tells TestBench to take screenshot before failure.
     */
    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this, true); //

    /*
     * Adds application URL that tests should open before trying to interact with the application.
     * IP: localhost, Port: 8080, Route: Information where to start from.
     */

    /* attributes */

    private static final String SERVER_HOST = IPAddress.findSiteLocalAddress();
    private static final int SERVER_PORT = 8080;
    private final String route;

    /* ----------------------------------------------------------------------------------- */

    /* logic */

    /**
     * Opens given URL in the browse.
     * @throws Exception if cannot open the browser, or the URL.
     */
    @Before
    public void setup() throws Exception {
        super.setup();
        getDriver().get(getURL(route)); // Opens the given URL in the browser
    }

    /**
     * @param route Setups route where to start test from.
     */
    protected AbstractTest(String route) {
        this.route = route;
    }

    /**
     * Start information in one place, with host, port and route.
     * @param route route where to start test from.
     * @return path to test URL (ip:port/route) in String format
     */
    private static String getURL(String route) {
        return String.format("http://%s:%d/%s", SERVER_HOST, SERVER_PORT, route);
    }

    /**
     *  This prevents excessive logging from WebDriverManager when running the tests
     */
    static {
        // Prevent debug logging from Apache HTTP client
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }
}
