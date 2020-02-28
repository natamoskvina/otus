package otusPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderMenu extends AbstractPage {

    private By loginButton = By.cssSelector("[data-modal-id='new-log-reg']");
    private By username = By.className("header2-menu__item-text__username");
    private By userDropdown = By.className("header2-menu__dropdown-text");
    private By learningDropdownOption = By.xpath("//a[contains(@class, 'header2-menu__dropdown-link_no-wrap') " +
                                "and contains(@href, 'learning')]");
    private By logoutDropdownOption = By.xpath("//a[contains(@class, 'header2-menu__dropdown-link_no-wrap') " +
            "and contains(@href, 'logout')]");

    public HeaderMenu(WebDriver driver) {
        super(driver);
    }

    @Override
    public HeaderMenu waitUntilRendered() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header2-menu_main")));
        return this;
    }

    public void goToUserAccount() {
        selectOptionFromRightDropdown(learningDropdownOption);
    }

    public void gotoLoginPage() {
        driver.findElement(loginButton).click();
    }

    public void logout() {
        selectOptionFromRightDropdown(logoutDropdownOption);
    }

    private void selectOptionFromRightDropdown(By dropdownOption) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(username)).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userDropdown));
        driver.findElement(dropdownOption).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(userDropdown));
    }
}
