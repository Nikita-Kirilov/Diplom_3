package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {
    private WebDriver webDriver;

    private By loginButton = By.xpath(".//a[@class='Auth_link__1fOlj']");

    private By forgotPasswordTextTitle = By.xpath(".//h2[text()='Восстановление пароля']");

    public ForgotPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Клик на кнопку \"Войти\" в форме восстановления пароля")
    public void clickLoginButtonOnForgotPasswordPage() {
        webDriver.findElement(loginButton).click();
    }

    @Step("Проверка открытия страницы \"Восстановление пароля\"")
    public void checkOpenForgotPasswordPage() {
        new WebDriverWait(webDriver, 3).until(driver -> (driver.findElement(forgotPasswordTextTitle)));
    }
}
