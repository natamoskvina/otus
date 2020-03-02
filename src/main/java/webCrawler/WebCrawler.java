package webCrawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebCrawler {

	private static final Logger logger = LogManager.getLogger(WebCrawler.class);

	public static void main(String[] args) throws FileNotFoundException {

		logger.info("Creating webdriver");
		WebDriver driver = Helper.driverSetup("chrome");

		Map<String, String> carMakesLinks = getCarMakes(driver);
		Set<String> carAdsLinks = getCarAdsLinks(driver, carMakesLinks);

		writeCarAdsToFile(driver, carAdsLinks);

		logger.info("Closing webdriver");
		Helper.teardown(driver);
		logger.info("Job finished!");

	}

	private static WebDriverWait setWebDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, 30);
	}

	private static void writeCarAdsToFile(WebDriver driver, Set<String> carAdsLinks) throws FileNotFoundException {
		PrintWriter writer = null;
		try {
			logger.info("Opening file");
			writer = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream("cars.csv"), StandardCharsets.UTF_8));

			writer.write(0xfeff);
			writer.println("Ссылка,Год,Цена,Марка,Модель,Объем двигателя");

			int currentAdd = 1;
			for (String link : carAdsLinks) {
				logger.info("Writing to the file: ad " + currentAdd++ + " out of " + carAdsLinks.size());
				writeCarInfoToFile(driver, writer, link);
			}
		} finally {
			closeWriter(writer);
		}
	}

	private static void writeCarInfoToFile(WebDriver driver, PrintWriter writer, String link) {
		String carInfo = getCarInfoString(driver, link);
		writer.println(carInfo);
	}

	private static void closeWriter(PrintWriter writer) {
		if (writer != null) {
			logger.info("Closing file");
			writer.close();
		}
	}

	private static String getCarInfoString(WebDriver driver, String link) {
		int curAttempt = 1;
		while (true) {
			try {
				driver.get(link);
				String year = getCarYear(driver);
				String price = getCarPrice(driver);
				String make = getCarMake(driver);
				String model = getCarModel(driver);
				String engineCap = getEngineCapacity(driver);
				return new StringJoiner(",").add(link).add(year).add(price).add(make).add(model).add(engineCap)
						.toString();

			} catch (WebDriverException e) {
				if (curAttempt == 5) {
					throw e;
				}
				curAttempt++;
				logger.error(e.getMessage());
				driver = recreateDriver();
			}
		}
	}

	private static String getCarModel(WebDriver driver) {
		By carModeSelector = By.xpath("//*[@data-ym-target='car2model']");
		if (!isElementPresent(driver, carModeSelector)) {
			carModeSelector = By.className("c-car-info__caption");
		}
		return driver.findElement(carModeSelector).getText();
	}

	private static String getCarMake(WebDriver driver) {
		By carMakeSelector = By.xpath("//*[@data-ym-target='car2brand']");
		String carMake = isElementPresent(driver, carMakeSelector) ? driver.findElement(carMakeSelector).getText() : "";
		return carMake;
	}

	private static String getCarPrice(WebDriver driver) {
		By carPriceSelector = By.xpath("//*[@class='c-car-forsale__price']/*[contains(text(), '₽')]");
		String carPrice = isElementPresent(driver, carPriceSelector) ? driver.findElement(carPriceSelector).getText()
				: "";
		return carPrice;
	}

	private static String getEngineCapacity(WebDriver driver) {
		By engineCapacitySelector = By.xpath("//*[@class='c-car-forsale__info']/li[contains(text(), 'Двигатель')]");
		String engineCapacity = isElementPresent(driver, engineCapacitySelector)
				? driver.findElement(engineCapacitySelector).getText().replaceAll(".*?([\\d.]+).*", "$1")
				: "";
		return engineCapacity;
	}

	private static String getCarYear(WebDriver driver) {
		By carYearSelector = By
				.xpath("//*[@class='c-car-forsale__info']/li[contains(text(), 'года') or contains(text(), 'году')]");
		String carYear = isElementPresent(driver, carYearSelector)
				? driver.findElement(carYearSelector).getText().replaceAll("[^0-9]", "").substring(0, 4)
				: "";
		return carYear;
	}

	private static Set<String> getCarAdsLinks(WebDriver driver, Map<String, String> carMakesLinks) {

		Set<String> carAdsLinks = new HashSet<>();
		int carMakesLeft = carMakesLinks.size() - 1;

		logger.info("Collecting ads");
		for (Map.Entry<String, String> carMakeLink : carMakesLinks.entrySet()) {
			try {
				driver.get(carMakeLink.getValue());
			} catch (RuntimeException e) {
				logger.error(e.getMessage());
				driver = recreateDriver();
				driver.get(carMakeLink.getValue());
			}

			logger.info("Getting ads for " + carMakeLink.getKey() + ". " + carMakesLeft-- + " car makes left.");
			scrollToTheEndOfPage(driver);

			List<WebElement> carAdsElements = driver
					.findElements(By.cssSelector(".c-car-card-sa.c-darkening-hover-container > a"));
			carAdsLinks.addAll(getLinks(carAdsElements));
			logger.info("Total ads: " + carAdsLinks.size());
		}
		return carAdsLinks;
	}

	private static WebDriver recreateDriver() {
		logger.info("Re-creating webdriver");
		return Helper.driverSetup("chrome");
	}

	private static void scrollToTheEndOfPage(WebDriver driver) {
		while (isElementPresent(driver, By.className("c-catalog-button"))) {
			int numberOfAdsBeforeScroll = driver
					.findElements(By.cssSelector(".c-car-card-sa.c-darkening-hover-container > a")).size();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebDriverWait wait = setWebDriverWait(driver);
			wait.until((d) -> d.findElements(By.cssSelector(".c-car-card-sa.c-darkening-hover-container > a"))
					.size() > numberOfAdsBeforeScroll);
		}
	}

	private static Set<String> getLinks(List<WebElement> linkElements) {
		Set<String> links = new HashSet<>();

		for (WebElement el : linkElements) {
			links.add(el.getAttribute("href"));
		}
		return links;
	}

	private static Map<String, String> getCarMakes(WebDriver driver) {
		WebDriverWait wait = setWebDriverWait(driver);

		driver.get("https://www.drive2.ru/cars/?sort=selling");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-slot='makeslist.more']")))
				.click();
		List<WebElement> carMakesElements = driver.findElements(By.cssSelector(".c-makes__item a"));

		logger.info("Getting all car makes");
		Map<String, String> links = new HashMap<>();
		for (WebElement el : carMakesElements) {
			links.putIfAbsent(el.getText(), el.getAttribute("href"));
		}
		return links;
	}

	private static boolean isElementPresent(WebDriver driver, By selector) {
		try {
			driver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
