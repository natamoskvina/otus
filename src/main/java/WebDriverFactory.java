import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    public static WebDriver create(BrowserType webDriverName) {
        return create(webDriverName, new MutableCapabilities());
    }

    public static WebDriver create(BrowserType webDriverName, MutableCapabilities options) {

        WebDriver driver = null;

        switch (webDriverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver((ChromeOptions) options);

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver((FirefoxOptions) options);

            default:
                throw new InvalidParameterException("No such browser found!");
        }
    }
}
