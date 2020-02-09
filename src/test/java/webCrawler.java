import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class webCrawler extends BrowserSetupTest {

    @Test
    public void testWebCrawler() {
        driver.get("https://www.drive2.ru/cars/?sort=selling");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("c-makes")));
        List<WebElement> carMakesElements = driver.findElements(By.cssSelector(".c-makes__item a"));
        Set<String> carMakesLinks = getCarMakesLinks(carMakesElements);

//        System.out.print(carMakesLinks.size());
//        System.out.println(Arrays.toString(carMakesLinks.toArray()));

        for (String carMakeLink : carMakesLinks) {
            driver.get(carMakeLink);

        }
    }

    private Set<String> getCarMakesLinks(List<WebElement> carMakesElements) {
        Set<String> links = new HashSet<>();

        for(WebElement el : carMakesElements) {
            links.add(el.getAttribute("href"));
        }
        return links;
    }

}
