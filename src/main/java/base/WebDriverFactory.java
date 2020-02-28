package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.security.InvalidParameterException;

public class WebDriverFactory {

    public static WebDriver create(BrowserType webDriverName) {
        return create(webDriverName, new MutableCapabilities());
    }

    public static WebDriver create(BrowserType webDriverName, MutableCapabilities options) {

        WebDriver driver = null;

        switch (webDriverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptions().merge(options));
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptions().merge(options));
            default:
                throw new InvalidParameterException("No such browser found!");
        }
    }
}
