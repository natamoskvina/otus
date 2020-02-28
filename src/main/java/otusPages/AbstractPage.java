package otusPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }
}
