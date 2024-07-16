package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthorizationPage {

    private WebDriver webDriver;

    private By emailTextField = By.xpath(".//input[@name='name']");
    private By passwordTextField = By.xpath(".//input[@name='Пароль']");
    private By registrationButton = By.xpath(".//a[text()='Зарегистрироваться']");
    private By forgotPasswordButton = By.xpath(".//a[text()='Восстановить пароль']");
    private By authorizationButton = By.xpath(".//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_medium__3zxIa']");
    private By loginTitleText = By.xpath(".//h2[text()='Вход']");



    public AuthorizationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Проверка открытия страницы \"Вход\"")
    public void checkOpenAuthorizationPage(String expectedTextLoginTitle) {
        String actualTextLoginTitle= webDriver.findElement(loginTitleText).getText();
        Assert.assertEquals(expectedTextLoginTitle,actualTextLoginTitle);
    }

    @Step("Ожидание открытия страницы \"Вход\"")
    public void waitOpenAuthorizationPage() {
        new WebDriverWait(webDriver, 3).until(driver -> (driver.findElement(loginTitleText)));
    }

    @Step("Клик на кнопку \"Регистрация\"")
    public void clickRegistrationButton() {
        webDriver.findElement(registrationButton).click();
    }

    @Step("Клик на кнопку \"Восстановить пароль\"")
    public void clickForgotPasswordButton() {
        webDriver.findElement(forgotPasswordButton).click();
    }

    @Step("Авторизация пользователя")
    public void userAuthorization(String userEmail, String userPassword) {
        webDriver.findElement(emailTextField).sendKeys(userEmail);
        webDriver.findElement(passwordTextField).sendKeys(userPassword);

        webDriver.findElement(authorizationButton).click();
    }

}
