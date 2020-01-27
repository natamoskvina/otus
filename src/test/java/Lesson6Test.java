import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Lesson6Test extends BrowserSetupTest {

    @Test
    public void yandexTest() {
        logger.info("Starting the test");
        logger.info("Opening market.yandex.ru");
        driver.get("https://ya.ru/");
        driver.get("https://market.yandex.ru/catalog--mobilnye-telefony/54726");

        logger.info("Selecting Xiaomi and HUAWEI");
        wait.until(visibilityOfElementLocated(By.className("n-snippet-cell2__title")));
        String firstProductInList = driver.findElement(By.cssSelector(".n-snippet-cell2__title a")).getAttribute("title");
        wait.until(visibilityOfElementLocated(By.xpath("//*[@name='Производитель Xiaomi']/following-sibling::div/span")))
                .click();
        wait.until(visibilityOfElementLocated(By.xpath("//*[@name='Производитель HUAWEI']/following-sibling::div/span")))
                .click();

        logger.info("Ordering by price");
        firstProductInList = driver.findElement(By.cssSelector(".n-snippet-cell2__title a")).getAttribute("title");
        driver.findElement(By.className("n-filter-panel-dropdown__main")).findElement(By.linkText("по цене")).click();
        wait.until(and(invisibilityOfElementLocated(By.cssSelector(".n-snippet-cell2__title a[title = '" + firstProductInList + "']")),
                invisibilityOfElementLocated(By.className("preloadable__preloader"))));

        logger.info("Adding Xiaomi phone to comparison");
        String XiaomiPhoneName = driver.findElement(By.cssSelector("img[title *= 'Смартфон Xiaomi']"))
                .getAttribute("title");
        driver.findElement(By.xpath("//a[contains(@title, 'Смартфон Xiaomi')]/..//i[contains(@class, 'image_name_compare')]"))
                .click();
        wait.until(driver -> driver.findElement(By.className("popup-informer__title")).getText()
                .equals("Товар " + XiaomiPhoneName + " добавлен к сравнению"));

        wait.until(ExpectedConditions.elementToBeClickable(By.className("popup-informer__close"))).click();

        logger.info("Adding Huawei phone to comparison");

        String HuaweiPhoneName = driver.findElement(By.cssSelector("img[title *= 'Смартфон HUAWEI']"))
                .getAttribute("title");
        driver.findElement(By.xpath("//a[contains(@title, 'Смартфон HUAWEI')]/..//i[contains(@class, 'image_name_compare')]"))
                .click();
        wait.until(drv -> drv.findElement(By.className("popup-informer__title")).getText()
                .equals("Товар " + HuaweiPhoneName + " добавлен к сравнению"));

        logger.info("Opening Compare page");
        driver.findElement(By.cssSelector(".popup-informer__pane a.button")).click();
        wait.until(drv -> drv.findElements(By.cssSelector(".n-compare-content__line .n-compare-cell")).size() == 2);

        logger.info("Opening all characteristics");
        driver.findElement(By.className("n-compare-show-controls__all")).click();
        wait.until(visibilityOfElementLocated(By.xpath("//*[contains(@class, 'n-compare-row-name') and text()='Операционная система']")));

        logger.info("Opening different characteristics");
        driver.findElement(By.className("n-compare-show-controls__diff")).click();
        wait.until(invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'n-compare-row-name') and text()='Операционная система']")));

        logger.info("Ending the test");
    }
}
