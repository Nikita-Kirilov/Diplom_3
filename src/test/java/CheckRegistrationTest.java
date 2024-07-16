import api.UserApi;
import apidata.User;
import constants.UrlConstants;
import changebrowser.Browser;
import changebrowser.ChoiceBrowserExamples;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.AuthorizationPage;
import pageobject.MainPage;
import pageobject.RegistrationPage;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CheckRegistrationTest {

    private static final String CHOICE_BROWSER = String.valueOf(ChoiceBrowserExamples.YANDEX);

    private static final String INCORRECT_PASSWORD_TEXT = "Некорректный пароль";
    private UserApi userApi;
    private String acessToken;

    private static final boolean keySuccessExpected = true;

    @Rule
    public Browser browser = new Browser();

    @Before
    public void setUp() {
        RestAssured.baseURI= UrlConstants.STELLAR_BURGERS_URL;
        WebDriverManager.chromedriver().setup();
    }
    @After
    public void tearDown() {
        //Удаление пользователя
        userApi.deleteUser(acessToken);
    }

    @Test
    @Description("Успешная регистрация пользователя")
    public void registrationUserCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.internet().password(6, 7);

        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickRegistrationButton();

        RegistrationPage registrationPage = new RegistrationPage(driver);

        //Реристрация пользователя
        registrationPage.registrationNewUser(userName,userEmail,userPassword);

        //Проверка регистрации пользователя через авторизацию апи и получение токена для удаления данных
        User user = new User()
                .withEmail(userEmail)
                .withPassword(userPassword);

        Response response = userApi.loginUser(user);
        acessToken = response.path("accessToken");
        acessToken = acessToken.substring(7);
        assertEquals("Неверный статус код", SC_OK, response.statusCode());
        boolean successActual = response.path("success");
        assertEquals("Некорректый ответ в Body success", keySuccessExpected, successActual);

    }

    @Test
    @Description("Регистрация пользователя с паполем менее 6 символов")
    public void registrationUserWithSmallPasswordCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.lorem().characters(5);

        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickRegistrationButton();

        RegistrationPage registrationPage = new RegistrationPage(driver);

        //Реристрация пользователя с некорректным паролем
        registrationPage.registrationNewUser(userName,userEmail,userPassword);
        //Проверка сообщения о некорректном пароле
        registrationPage.incorrectPasswordCheck(INCORRECT_PASSWORD_TEXT);
    }
}
