package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver webDriver;

    private By nameTextField = By.xpath(".//fieldset[1]/div/div/input[@name='name']");
    private By emailTextField = By.xpath(".//fieldset[2]/div/div/input[@name='name']");
    private By passwordTextField = By.xpath(".//input[@name='Пароль']");
    private By registrationButton = By.xpath(".//button[text()='Зарегистрироваться']");

    private By loginButton = By.xpath(".//a[@class='Auth_link__1fOlj']");

    private By incorrectPasswordText = By.xpath(".//p[text()='Некорректный пароль']");

    public RegistrationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Регистрация пользователя")
    public void registrationNewUser(String userName, String userEmail, String userPassword) {
        webDriver.findElement(nameTextField).sendKeys(userName);
        webDriver.findElement(emailTextField).sendKeys(userEmail);
        webDriver.findElement(passwordTextField).sendKeys(userPassword);

        webDriver.findElement(registrationButton).click();
    }

    @Step("Проверка некорректно введенного пароля")
    public void incorrectPasswordCheck(String textIncorrectPassExpected) {
        String actualIncorrectPassText= webDriver.findElement(incorrectPasswordText).getText();
        Assert.assertEquals(textIncorrectPassExpected,actualIncorrectPassText);
    }

    @Step("Клик на кнопку \"Войти\" в форме регистрации")
    public void clickLoginButtonOnRegistrationPage() {
        webDriver.findElement(loginButton).click();
    }
}
