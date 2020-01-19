import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class BrowserSetupTest {

    private WebDriver driver;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class.getName());

    @BeforeTest
    @Parameters({ "browser" })
    public void setupTest(String browserName) {
        driver = WebDriverFactory.create(browserName);
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
        logger.trace("End test");
    }
}
