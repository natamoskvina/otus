package otusPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {

    private By loginModal = By.cssSelector("[data-modal-id='new-log-reg']");
    private By emailInput = By.className("js-email-input");
    private By passwordInput = By.className("js-psw-input");
    private By loginButton = By.cssSelector("form.js-login button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage waitUntilRendered() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        return this;
    }

    public HomePage loginUser(String username, String password) {
        driver.findElement(emailInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
        return new HomePage(driver).waitUntilRendered();
    }
}
