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
import pageobject.AccountProfilePage;
import pageobject.AuthorizationPage;
import pageobject.MainPage;

import static driver.WebDriverCreator.createWebDriver;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CheckAccountProfileTest {

    WebDriver driver;
    private static final String LOGIN_TITLE_TEXT = "Вход";

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
    @Description("Проверка выхода из аккаунта по кнопке \"Выход\"")
    public void logoutFromAccountProfileCheck() {
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
        //Открыть профиль пользователя
        mainPage.openAuthorizationPageFromHeader();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);

        accountProfilePage.waitOpenAccountProfilePage();
        //Клик на кнопку выхода
        accountProfilePage.clickLogoutButton();
        //Проверка выхода из аккаунта
        authorizationPage.waitOpenAuthorizationPage();
        authorizationPage.checkOpenAuthorizationPage(LOGIN_TITLE_TEXT);

    }

}
