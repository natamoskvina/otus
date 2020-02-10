import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebCrawler extends BrowserSetupTest {

    @Test
    public void testWebCrawler() throws IOException {
//        driver.get("https://www.drive2.ru/cars/?sort=selling");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-slot='makeslist.more']"))).click();
//        List<WebElement> carMakesElements = driver.findElements(By.cssSelector(".c-makes__item a"));
//        Map<String, String> carMakesLinks = getCarMakes(carMakesElements);
        Map<String, String> carMakesLinks = new HashMap<>();
//
//        carMakesLinks.put("Alfa Romeo", "https://www.drive2.ru/cars/alfaromeo/?sort=selling"); //30
        carMakesLinks.put("Mini","https://www.drive2.ru/cars/mini/?sort=selling"); //8
//        carMakesLinks.put("Volkswagen", "https://www.drive2.ru/cars/volkswagen/?sort=selling"); //514
//        carMakesLinks.put("Ford", "https://www.drive2.ru/cars/ford/?sort=selling"); //303

        Set<String> carAdsLinks = getCarAdsLinks(carMakesLinks);
//        Set<String> carAdsLinks = new HashSet<>();
//        carAdsLinks.add("https://www.drive2.ru/r/ford/focus_sedan/486446760068121333/");
//        carAdsLinks.add("https://www.drive2.ru/r/ford/escape/843919/");

        writeCarAdsToFile(carAdsLinks);
    }

    private void writeCarAdsToFile(Set<String> carAdsLinks) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("cars.csv"), StandardCharsets.UTF_8));
        writer.write(0xfeff);
        writer.println("Ссылка,Год,Цена,Марка,Модель,Объем двигателя");
        int currentAdd = 1;
        for(String link : carAdsLinks) {
            logger.info("Writing to the file ad " + currentAdd + " out of " + carAdsLinks.size());
            String carInfo = getCarInfoString(link);
            writer.println(carInfo);
            currentAdd++;
        }
        writer.close();
        logger.info("Job successfully finished!");
    }

    private String getCarInfoString(String link) {
        driver.get(link);
        String year = getCarYear();
        String price = getCarPrice();
        String make = getCarMake();
        String model = getCarModel();
        String engineCap = getEngineCapacity();
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(link).add(year).add(price).add(make).add(model).add(engineCap);
        return joiner.toString();
    }

    private String getCarModel() {
        return driver.findElement(By.xpath("//*[@data-ym-target='car2model']")).getText();
    }

    private String getCarMake() {
        return driver.findElement(By.xpath("//*[@data-ym-target='car2brand']")).getText();
    }

    private String getCarPrice() {
        return driver.findElement(By.xpath("//*[@class='c-car-forsale__price']/*[contains(text(), '₽')]")).getText();
    }

    private String getEngineCapacity() {
        String engineText = driver.findElement(
                By.xpath("//*[@class='c-car-forsale__info']/li[contains(text(), 'Двигатель')]"))
                .getText();
        return engineText.replaceAll(".*?([\\d.]+).*", "$1");
    }

    private String getCarYear() {
        String yearText = driver.findElement(
                By.xpath("//*[@class='c-car-forsale__info']/li[contains(text(), 'года') or contains(text(), 'году')]"))
                .getText();
        return yearText.replaceAll("[^0-9?!\\.]","").substring(0, 4);
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
