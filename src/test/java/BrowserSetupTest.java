import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BrowserSetupTest {

    protected WebDriver driver;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class);

    @BeforeTest
    @Parameters({ "browser" })
    public void setupTest(@Optional ("chrome") String browserName) {
        driver = WebDriverFactory.create(BrowserType.get(browserName.toLowerCase()));
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
