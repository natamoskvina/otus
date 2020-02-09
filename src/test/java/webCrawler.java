import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.*;

public class webCrawler extends BrowserSetupTest {

    @Test
    public void testWebCrawler() {
        driver.get("https://www.drive2.ru/cars/?sort=selling");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-slot='makeslist.more']"))).click();
        List<WebElement> carMakesElements = driver.findElements(By.cssSelector(".c-makes__item a"));
        Map<String, String> carMakesLinks = getCarMakes(carMakesElements);
//        Map<String, String> carMakesLinks = new HashMap<>();
//
//        carMakesLinks.put("Alfa Romeo", "https://www.drive2.ru/cars/alfaromeo/?sort=selling"); //30
//        carMakesLinks.put("Mini","https://www.drive2.ru/cars/mini/?sort=selling"); //8
//        carMakesLinks.put("Volkswagen", "https://www.drive2.ru/cars/volkswagen/?sort=selling"); //514
//        carMakesLinks.put("Ford", "https://www.drive2.ru/cars/ford/?sort=selling"); //303

        Set<String> carAdsLinks = getCarAdsLinks(carMakesLinks);

    }

    private Set<String> getCarAdsLinks(Map<String, String> carMakesLinks) {

        Set<String> carAdsLinks = new HashSet<>();

        for (Map.Entry<String, String> carMakeLink : carMakesLinks.entrySet()) {
            driver.get(carMakeLink.getValue());
            logger.info("Getting ads for " + carMakeLink.getKey());
            while (isElementPresent(By.className("c-catalog-button"))) {
                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            }

            List<WebElement> carAdsElements = driver.findElements(By.cssSelector(".c-car-card-sa.c-darkening-hover-container > a"));
            carAdsLinks.addAll(getLinks(carAdsElements));
            logger.info("Total ads: " + carAdsLinks.size());
        }
        return carAdsLinks;
    }

    private Set<String> getLinks(List<WebElement> webElements) {
        Set<String> links = new HashSet<>();

        for(WebElement el : webElements) {
            links.add(el.getAttribute("href"));
        }
        return links;
    }

    private Map<String, String> getCarMakes(List<WebElement> webElements) {
        Map<String, String> links = new HashMap<>();

        for(WebElement el : webElements) {
            links.putIfAbsent(el.getText(), el.getAttribute("href"));
        }
        return links;
    }

    private boolean isElementPresent(By selector) {
        try {
            driver.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
