import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BrowserSetupTest {

    private WebDriver driver;
    private Browser browser;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class.getName());

    public BrowserSetupTest(Browser browser) {
        this.browser = browser;
    }

    @Before
    public void setupTest() {
        BrowserDriverFactory factory = new BrowserDriverFactory(browser);
        driver = factory.createDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Object[] browsers() {
        return new Object[] { Browser.CHROME, Browser.FIREFOX };
    }

    @Test
    public void test() {
        logger.trace("Start test");
        String url = "https://otus.ru/";
        driver.get(url);
        logger.error("This is error!");
        logger.trace("End test");
    }
}
