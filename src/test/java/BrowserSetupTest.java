import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class BrowserSetupTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class);

    @BeforeTest
    @Parameters({ "browser" })
    public void setupTest(@Optional ("chrome") String browserName) {
        logger.info("Creating webdriver: " + browserName);
        driver = WebDriverFactory.create(BrowserType.get(browserName.toLowerCase()));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            logger.info("Closing webdriver");
            driver.quit();
        }
    }
}
