import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.AuthorizationPage;
import pageobject.MainPage;

import static driver.WebDriverCreator.createWebDriver;

public class CheckTransitionsTest {
    WebDriver driver;
    private static final String LOGIN_TITLE_TEXT = "Вход";

    private static final String CONSTRUCTOR_TITLE_TEXT = "Соберите бургер";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Description("Проверка перехода в «Личный кабинет»")
    public void goToPersonalAccountCheck() {
        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        //Проверка открытия страницы Входа
        authorizationPage.checkOpenAuthorizationPage(LOGIN_TITLE_TEXT);
    }

    @Test
    @Description("Проверка перехода из личного кабинета в конструктор по кнопке «Конструктор»")
    public void goToMainPageWithConstructorButtonCheck() {
        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        //Проверка открытия страницы Конструктора
        mainPage.openMainPageFromConstructorButton();
        mainPage.checktextTitleOrder(CONSTRUCTOR_TITLE_TEXT);
    }

    @Test
    @Description("Проверка перехода из личного кабинета в конструктор по кнопке «Stellar burgers»")
    public void goToMainPageWithLogoButtonCheck() {
        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        //Проверка открытия страницы Конструктора
        mainPage.openMainPageFromLogoButton();
        mainPage.checktextTitleOrder(CONSTRUCTOR_TITLE_TEXT);
    }
}
