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
 * @version 31.5.2020
 * Test Class.
 */

/* TODO Documentation */


public abstract class AbstractTest extends ParallelTest {

    /* ----------------------------------------------------------------------------------- */

    /* vaadin */

    @BeforeClass
    public static void setupClass() {

        // Starts Chrome Web Driver Manager
        WebDriverManager.chromedriver().setup(); //
    }

    /* Tells TestBench to take Screenshot before failure. */
    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this, true); //

    /* Following code adds application URL that the tests should open before trying to interact with the
     * application. For this test needs the host name where the application runs
     * ("localhost" in development), the port the server uses
     * (set to 8080 in application.properties), and information about the route to start from.
     */

    private static final String SERVER_HOST = IPAddress.findSiteLocalAddress();
    private static final int SERVER_PORT = 8080;
    private final String route;

    @Before
    public void setup() throws Exception {
        super.setup();
        getDriver().get(getURL(route)); // Opens the given URL in the browser
    }

    protected AbstractTest(String route) {
        this.route = route;
    }

    private static String getURL(String route) {
        return String.format("http://%s:%d/%s", SERVER_HOST, SERVER_PORT, route);
    }

    /* This prevents excessive logging from WebDriverManager when running the tests */
    static {
        // Prevent debug logging from Apache HTTP client
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }
}
