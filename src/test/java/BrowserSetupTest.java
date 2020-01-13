import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.Collection;

public class BrowserSetupTest {

    private WebDriver driver;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class.getName());

    @BeforeTest
    @Parameters({ "browser" })
    public void setupTest(@Optional("chrome") String browser) {
        BrowserDriverFactory factory = new BrowserDriverFactory(Browser.valueOf(browser.toUpperCase()));
        driver = factory.createDriver();
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        logger.trace("Start test");
        String url = "https://otus.ru/";
        driver.get(url);
        logger.debug("Debug Message!");
        logger.info("Info Message!");
        logger.error("Error Message!");
        logger.fatal("Fatal Message!");
    }
}
