package otusPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserAccountPage extends AbstractPage {

    private HeaderMenu headerMenu;

    private By personalInfoLink = By.xpath("//*[@class='nav__items']/a[contains(@href, 'personal')]");
    private By personalInfoForm = By.xpath("//form[contains(@action, 'personal')]");
    private By firstnameInput = By.id("id_fname");
    private By lastnameInput = By.id("id_lname");
    private By countryDropdown = By.xpath("//*[@name='country']/following-sibling::div");
    private By countryList = By.className("lk-cv-block__select-scroll_country");
    private By cityDropdown = By.xpath("//*[@name='city']/following-sibling::div");
    private By cityList = By.className("lk-cv-block__select-scroll_city");
    private By saveButton = By.xpath("//*[@class = 'lk-cv-action-buttons']/*[@name='continue']");

    public UserAccountPage(WebDriver driver) {
        super(driver);
        headerMenu = new HeaderMenu(driver);
    }

    public UserAccountPage waitUntilRendered() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("nav__items")));
        return this;
    }

    public UserAccountPage goToPersonalInfoSection() {
        driver.findElement(personalInfoLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalInfoForm));
        return this;
    }

    public UserAccountPage enterFirstname(String firstname) {
        driver.findElement(firstnameInput).clear();
        driver.findElement(firstnameInput).sendKeys(firstname);
        return this;
    }

    public UserAccountPage enterLastname(String lastname) {
        driver.findElement(lastnameInput).clear();
        driver.findElement(lastnameInput).sendKeys(lastname);
        return this;
    }

    public UserAccountPage selectCountry(String country) {
        driver.findElement(countryDropdown).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryList));
        driver.findElement(By.xpath("//button[contains(@class, 'lk-cv-block__select-option') and @title='" + country + "']")).click();
        wait.until((d) -> d.findElement(By.name("city")).isEnabled());
        return this;
    }

    public UserAccountPage selectCity(String city) {
        driver.findElement(cityDropdown).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityList));
        driver.findElement(By.xpath("//button[contains(@class, 'lk-cv-block__select-option') and @title='" + city + "']")).click();
        return this;
    }

    public UserAccountPage saveChanges() {
        driver.findElement(saveButton).click();
        wait.until((d) -> d.findElements(personalInfoForm).size() == 0);
        return new UserAccountPage(driver).waitUntilRendered();
    }

    public HomePage logout() {
        headerMenu.logout();
        return new HomePage(driver).waitUntilRendered();
    }

    public String getFirstname() {
        return driver.findElement(firstnameInput).getAttribute("value");
    }

    public String getLastname() {
        return driver.findElement(lastnameInput).getAttribute("value");
    }

    public String getCountry() {
        return driver.findElement(countryDropdown).getText().trim();
    }

    public String getCity() {
        return driver.findElement(cityDropdown).getText().trim();
    }
}
