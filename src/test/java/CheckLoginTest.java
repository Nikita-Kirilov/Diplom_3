import api.UserApi;
import apidata.User;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.AuthorizationPage;
import pageobject.ForgotPasswordPage;
import pageobject.MainPage;
import pageobject.RegistrationPage;

import static driver.WebDriverCreator.createWebDriver;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CheckLoginTest {
    WebDriver driver;
    private static final String ORDER_BUTTON_TEXT = "Оформить заказ";

    private UserApi userApi;
    private String acessToken;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }
    @After
    public void tearDown() {
        //Удаление пользователя
        userApi.deleteUser(acessToken);
        driver.quit();
    }

    @Test
    @Description("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginUserMainButtonCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.internet().password(6, 7);
        //Создание тестового пользователя
        User user = new User()
                .withEmail(userEmail)
                .withPassword(userPassword)
                .withName(userName);

        Response response = userApi.postCreateUser(user);
        acessToken = response.path("accessToken");
        acessToken = acessToken.substring(7);
        assertEquals("Неверный статус код", SC_OK, response.statusCode());

        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromMain();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        //Ожидание открытия страницы Входа
        authorizationPage.waitOpenAuthorizationPage();
        //Авторизация
        authorizationPage.userAuthorization(userEmail,userPassword);
        //Проверка авторизации
        mainPage.waitOpenMainPage();
        mainPage.checkTextInOrderButton(ORDER_BUTTON_TEXT);
    }

    @Test
    @Description("Вход через кнопку «Личный кабинет»")
    public void loginUserHeaderButtonCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.internet().password(6, 7);
        //Создание тестового пользователя
        User user = new User()
                .withEmail(userEmail)
                .withPassword(userPassword)
                .withName(userName);

        Response response = userApi.postCreateUser(user);
        acessToken = response.path("accessToken");
        acessToken = acessToken.substring(7);
        assertEquals("Неверный статус код", SC_OK, response.statusCode());

        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        //Ожидание открытия страницы Входа
        authorizationPage.waitOpenAuthorizationPage();
        //Авторизация
        authorizationPage.userAuthorization(userEmail,userPassword);
        //Проверка авторизации
        mainPage.waitOpenMainPage();
        mainPage.checkTextInOrderButton(ORDER_BUTTON_TEXT);
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void loginUserFromRegistrationPageCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.internet().password(6, 7);
        //Создание тестового пользователя
        User user = new User()
                .withEmail(userEmail)
                .withPassword(userPassword)
                .withName(userName);

        Response response = userApi.postCreateUser(user);
        acessToken = response.path("accessToken");
        acessToken = acessToken.substring(7);
        assertEquals("Неверный статус код", SC_OK, response.statusCode());

        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);

        authorizationPage.clickRegistrationButton();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginButtonOnRegistrationPage();


        //Ожидание открытия страницы Входа
        authorizationPage.waitOpenAuthorizationPage();
        //Авторизация
        authorizationPage.userAuthorization(userEmail,userPassword);
        //Проверка авторизации
        mainPage.waitOpenMainPage();
        mainPage.checkTextInOrderButton(ORDER_BUTTON_TEXT);
    }

    @Test
    @Description("вход через кнопку в форме восстановления пароля")
    public void loginUserFromRecoveryPasswordPageCheck() {
        Faker faker = new Faker();
        userApi = new UserApi();
        String userName = faker.name().firstName();
        String userEmail = faker.internet().safeEmailAddress();
        String userPassword = faker.internet().password(6, 7);
        //Создание тестового пользователя
        User user = new User()
                .withEmail(userEmail)
                .withPassword(userPassword)
                .withName(userName);

        Response response = userApi.postCreateUser(user);
        acessToken = response.path("accessToken");
        acessToken = acessToken.substring(7);
        assertEquals("Неверный статус код", SC_OK, response.statusCode());

        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .openAuthorizationPageFromHeader();

        AuthorizationPage authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickForgotPasswordButton();

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.checkOpenForgotPasswordPage();
        forgotPasswordPage.clickLoginButtonOnForgotPasswordPage();

        //Ожидание открытия страницы Входа
        authorizationPage.waitOpenAuthorizationPage();
        //Авторизация
        authorizationPage.userAuthorization(userEmail,userPassword);
        //Проверка авторизации
        mainPage.waitOpenMainPage();
        mainPage.checkTextInOrderButton(ORDER_BUTTON_TEXT);
    }

}
