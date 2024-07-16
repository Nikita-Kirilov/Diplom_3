import changebrowser.Browser;
import changebrowser.ChoiceBrowserExamples;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;

@RunWith(Parameterized.class)
public class TransitionsByIngredientsTest {
    //Для теста, нужен чтобы перейти сначала к 3 элементу(Начинки), чтобы точно знать, что переход осуществляется
    private final int thirdIngredient;
    private final int ingredientExpected;
    private final String ingredientTitleNameExpected;

    private static final String CHOICE_BROWSER = String.valueOf(ChoiceBrowserExamples.CHROME);

    @Rule
    public Browser browser = new Browser();

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    public TransitionsByIngredientsTest(int thirdIngredient, String ingredientTitleNameExpected, int ingredientExpected) {
        this.thirdIngredient = thirdIngredient;
        this.ingredientTitleNameExpected = ingredientTitleNameExpected;
        this.ingredientExpected = ingredientExpected;
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][] {
                {2,"Булки",0},
                {2,"Соусы",1},
                {1,"Начинки",2},
        };
    }

    @Test
    @Description("Проверка перехода к разделам ингридиентов")
    public void goToIngredientInContainerCheck() {
        WebDriver driver = browser.getWebDriver(CHOICE_BROWSER);

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTransitionByIngredients(thirdIngredient);
        mainPage.clickTransitionByIngredients(ingredientExpected);
        //Сравнение имена заголовка внутри контейнера меню
        mainPage.checkTransitionByIngredientsNameInContainer(ingredientExpected,ingredientTitleNameExpected);
    }
}
