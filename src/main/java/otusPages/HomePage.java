package otusPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends AbstractPage {

    private HeaderMenu headerMenu;

    private String baseUrl = "https://otus.ru/";

    public HomePage(WebDriver driver) {
        super(driver);
        headerMenu = new HeaderMenu(driver);
    }

    public HomePage waitUntilRendered() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("categories_id")));
        return this;
    }

    public HomePage open() {
        driver.get(baseUrl);
        return new HomePage(driver).waitUntilRendered();
    }

    public LoginPage openLoginPage() {
        headerMenu.gotoLoginPage();
        return new LoginPage(driver).waitUntilRendered();
    }

    public UserAccountPage openUserAccount() {
        headerMenu.goToUserAccount();
        return new UserAccountPage(driver).waitUntilRendered();
    }
}
