import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    public static WebDriver create(String webDriverName) {
        return create(webDriverName, null);
    }

    public static WebDriver create(String webDriverName, MutableCapabilities options) {

        WebDriver driver = null;

        switch (BrowserType.valueOf(webDriverName.toUpperCase())) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = options != null ? new ChromeDriver((ChromeOptions) options) : new ChromeDriver();
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = options != null ? new FirefoxDriver((FirefoxOptions) options) : new FirefoxDriver();
                break;
        }
        return driver;
    }
}
