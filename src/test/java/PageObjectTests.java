import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import otusPages.HomePage;
import otusPages.LoginPage;
import otusPages.UserAccountPage;

public class PageObjectTests extends BrowserSetup {

    @Test
    public void personalInfoIsSaved() {

        String email = "natamoskvina01@yandex.ru";
        String password = "Abc12345";

        logger.info("Starting the test");
        logger.info("Opening Otus homepage");
        HomePage homePage = new HomePage(driver).open();

        logger.info("Logging in");
        LoginPage loginPage = homePage.openLoginPage();
        homePage = loginPage.loginUser(email, password);

        logger.info("Navigating to personal info page");
        UserAccountPage userAccountPage = homePage.openUserAccount().goToPersonalInfoSection();

        logger.info("Entering personal info");
        userAccountPage = userAccountPage
                .enterFirstname("Наталия")
                .enterLastname("Москвина")
                .selectCountry("Россия")
                .selectCity("Челябинск")
                .saveChanges();

        logger.info("Logging out");
        homePage = userAccountPage.logout();

        logger.info("Re-logging in");
        homePage.openLoginPage();
        homePage = loginPage.loginUser(email, password);

        logger.info("Navigating to personal info page");
        userAccountPage = homePage.openUserAccount().goToPersonalInfoSection();

        String expectedFirstName = userAccountPage.getFirstname();
        String expectedLastName = userAccountPage.getLastname();
        String expectedCountry = userAccountPage.getCountry();
        String expectedCity = userAccountPage.getCity();

        logger.info("Verifying that personal info has been saved");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(expectedFirstName, "Наталия");
        softAssert.assertEquals(expectedLastName, "Москвина");
        softAssert.assertEquals(expectedCountry, "Россия");
        softAssert.assertEquals(expectedCity, "Челябинск");
        softAssert.assertAll();
    }
}
