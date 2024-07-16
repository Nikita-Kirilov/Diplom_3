package pageobject;

import constants.UrlConstants;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {

    private WebDriver webDriver;

    private String numberOfIngredients;

    private By personalAccountButtonHeader = org.openqa.selenium.By.xpath(".//a[@href='/account']");

    private By buttonLoginOrOrderMain = org.openqa.selenium.By.xpath(".//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");

    private By textTitleOrder = org.openqa.selenium.By.xpath(".//h1[@class='text text_type_main-large mb-5 mt-10']");

    private By constructorButton = org.openqa.selenium.By.xpath(".//p[text()='Конструктор']");

    private By logoButton = org.openqa.selenium.By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']/a");

    private By burgerIngredientsButton = org.openqa.selenium.By.xpath(".//section[@class='BurgerIngredients_ingredients__1N8v2']/div/div");

    private By burgerTitleNameIngredientInContainer = org.openqa.selenium.By.xpath(".//div[@class='BurgerIngredients_ingredients__menuContainer__Xu3Mo']/h2");
    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public MainPage open() {
        webDriver.get(UrlConstants.STELLAR_BURGERS_URL);
        return this;
    }
    @Step("Клик на кнопку \"Личный кабинет\" в шапке сайта")
    public void openAuthorizationPageFromHeader() {
        webDriver.findElement(personalAccountButtonHeader).click();
    }

    @Step("Клик на кнопку \"Войти в аккаунт\" на главной странице")
    public void openAuthorizationPageFromMain() {
        webDriver.findElement(buttonLoginOrOrderMain).click();
    }

    @Step("Клик на кнопку \"Конструктор\" на главной странице")
    public void openMainPageFromConstructorButton() {
        webDriver.findElement(constructorButton).click();
    }

    @Step("Клик на кнопку \"Stellar burgers\" на главной странице")
    public void openMainPageFromLogoButton() {
        webDriver.findElement(logoButton).click();
    }

    @Step("Ожидание открытия главной страницы")
    public void waitOpenMainPage() {
        new WebDriverWait(webDriver, 5).until(driver -> (driver.findElement(buttonLoginOrOrderMain)));
    }

    @Step("Проверка текста кнопки \"Войти\" / \"Оформить заказ\"")
    public void checkTextInOrderButton(String orderButtonTextExpected) {
        String actualOrderButtonText= webDriver.findElement(buttonLoginOrOrderMain).getText();
        Assert.assertEquals(orderButtonTextExpected,actualOrderButtonText);
    }

    @Step("Проверка перехода на страницу конструктора")
    public void checktextTitleOrder(String textTitleOrderExpected) {
        String textTitleOrderActual= webDriver.findElement(textTitleOrder).getText();
        Assert.assertEquals(textTitleOrderExpected,textTitleOrderActual);
    }

    @Step("Клик на элемент ингридиентов")
    public void clickTransitionByIngredients(int numberOfIngredients) {
        List<WebElement> calendarDateList = webDriver.findElements(burgerIngredientsButton);
        calendarDateList.get(numberOfIngredients).click();
    }

    @Step("Проверка перехода к определенному элементу ингридиентов в конструкторе")
    public void checkTransitionByIngredientsNameInContainer(int numberOfIngredients, String nameOfIngredientTitleInContainerExpected) {
        List<WebElement> calendarDateList = webDriver.findElements(burgerTitleNameIngredientInContainer);
        String actualburgerTitleNameIngredientText= calendarDateList.get(numberOfIngredients).getText();
        Assert.assertEquals(nameOfIngredientTitleInContainerExpected,actualburgerTitleNameIngredientText);
    }
}
