package webCrawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;

import base.BrowserType;
import base.WebDriverFactory;

public class Helper {
	
	public static WebDriver driverSetupTest(String browserName) {
        WebDriver driver = WebDriverFactory.create(BrowserType.get(browserName.toLowerCase()));
        driver.manage().window().maximize();
        return driver;
    }

    public static void teardown(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

}
