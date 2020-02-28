package otusPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderMenu extends AbstractPage {

    private By loginButton = By.cssSelector("[data-modal-id='new-log-reg']");
    private By username = By.className("header2-menu__item-text__username");
    private By userDropdown = By.className("header2-menu__dropdown-text");
    private By userAccountLink = By.xpath("//a[contains(@class, 'header2-menu__dropdown-link_no-wrap') " +
                                "and contains(@href, 'learning')]");
    private By logoutLink = By.xpath("//a[contains(@class, 'header2-menu__dropdown-link_no-wrap') " +
            "and contains(@href, 'logout')]");

    public HeaderMenu(WebDriver driver) {
        super(driver);
    }

    public void goToUserAccount() {
        openUserActionsMenu();
        driver.findElement(userAccountLink).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(userDropdown));
    }

    public void gotoLoginPage() {
        driver.findElement(loginButton).click();
    }

    public void logout() {
        openUserActionsMenu();
        driver.findElement(logoutLink).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(userDropdown));
    }

    private void openUserActionsMenu() {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(username)).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userDropdown));
    }
}
