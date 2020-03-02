package webCrawler;

import base.BrowserType;
import base.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class Helper {
	
	public static WebDriver driverSetup(String browserName) {
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
