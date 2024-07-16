import changebrowser.Browser;
import changebrowser.ChoiceBrowserExamples;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.AuthorizationPage;
import pageobject.MainPage;

public class CheckTransitionsTest {
    private static final String CHOICE_BROWSER = String.valueOf(ChoiceBrowserExamples.YANDEX);

    private static final String LOGIN_TITLE_TEXT = "Вход";

    private static final String CONSTRUCTOR_TITLE_TEXT = "Соберите бургер";

    @Rule
    public Browser browser = new Browser();

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    @Description("Проверка перехода в «Личный кабинет»")
    public void goToPersonalAccountCheck() {
        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

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
        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

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
        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        //Проверка открытия страницы Конструктора
        mainPage.openMainPageFromLogoButton();
        mainPage.checktextTitleOrder(CONSTRUCTOR_TITLE_TEXT);
    }
}
