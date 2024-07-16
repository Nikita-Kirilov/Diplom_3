package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountProfilePage {

    private WebDriver webDriver;

    private By logoutButton = By.xpath(".//button[text()='Выход']");

    private By staticTextAboutAccountProfile = By.xpath(".//p[@class='Account_text__fZAIn text text_type_main-default']");

    public AccountProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Клик на кнопку \"Выход\"")
    public void clickLogoutButton() {
        webDriver.findElement(logoutButton).click();
    }

    @Step("Ожидание открытия страницы  профиля пользователя")
    public void waitOpenAccountProfilePage() {
        new WebDriverWait(webDriver, 3).until(driver -> (driver.findElement(staticTextAboutAccountProfile)));
    }
}
