import base.BrowserType;
import base.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Listeners(ExecutionListener.class)
public class BrowserSetupTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PrintWriter writer;
    static final Logger logger = LogManager.getLogger(BrowserSetupTest.class);

    @BeforeTest
    @Parameters({ "browser" })
    public void setupTest(@Optional ("chrome") String browserName, ITestContext context) throws FileNotFoundException {
        logger.info("Creating webdriver: " + browserName);
        driver = WebDriverFactory.create(BrowserType.get(browserName.toLowerCase()));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
        logger.info("Opening file");
        writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("cars.csv"), StandardCharsets.UTF_8));

        context.setAttribute("driver", driver);
        context.setAttribute("logger", logger);
    }

    @AfterTest
    public void teardown() {
        if (writer != null) {
            logger.info("Closing file");
            writer.close();
        }

        if (driver != null) {
            logger.info("Closing webdriver");
            driver.quit();
        }
        logger.info("Job finished!");
    }
}
