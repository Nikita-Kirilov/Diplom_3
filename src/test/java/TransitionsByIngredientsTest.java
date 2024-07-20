import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;

import static driver.WebDriverCreator.createWebDriver;

@RunWith(Parameterized.class)
public class TransitionsByIngredientsTest {
    WebDriver driver;
    private final int ingredientExpected;
    private final String ingredientTitleNameExpected;
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    public TransitionsByIngredientsTest(String ingredientTitleNameExpected, int ingredientExpected) {
        this.ingredientTitleNameExpected = ingredientTitleNameExpected;
        this.ingredientExpected = ingredientExpected;
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][] {
                {"Булки",0},
                {"Соусы",1},
                {"Начинки",2},
        };
    }

    @Test
    @Description("Проверка перехода к разделам ингридиентов")
    public void goToIngredientInContainerCheck() {
        driver = createWebDriver();

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.transitionToElementIngredients(ingredientExpected);
        //Проверка выбора значения ингридиента из меню, путем сравнения проскроленного типа ингридиента в контейнере и типа ингридиента из меню (параметр класс содержит type_current)
        mainPage.checkTransitionByIngredientsNameInContainerOne(ingredientTitleNameExpected);
    }
}
