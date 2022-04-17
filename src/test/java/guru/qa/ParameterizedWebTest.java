package guru.qa;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Keys;

import java.util.stream.Stream;

@DisplayName("Проверка товаров на сайте")
public class ParameterizedWebTest {

    SelenideElement searchImput = Selenide.$("#catalogSearch");

    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll");
        Selenide.open("https://www.21vek.by/");
    }

    @BeforeEach
    void beforeEach() {
        // тест для Мака только!!!
        searchImput.sendKeys(Keys.COMMAND + "a");
        searchImput.sendKeys(Keys.BACK_SPACE);
    }

    @ParameterizedTest(name = "проверка моделей {0} по соответсвию значениям: {1}")
    @CsvSource(value = {
            "LG Steam F1296NDS0, стиральная машина",
            "Bosch SPV2HKX5DR, посудомоечная машина"
    })
    void searchTest(String testData, String expectedResult) {

        searchImput.setValue(testData).pressEnter();
        Selenide.$$("li.result__item")
                .first()
                .shouldHave(Condition.text(expectedResult));

    }

    @ParameterizedTest(name = "проверка поиска на соотвествие значения: {0}")
    @ValueSource(strings = {"стиральная машина", "посудомоечная машина"})
    void searchTest2(String testData) {

        searchImput.setValue(testData).pressEnter();
        Selenide.$$("li.result__item")
                .first()
                .shouldHave(Condition.text(testData));

    }

    static Stream<Arguments> argsMethodSource() {
        return Stream.of(
                Arguments.of("LG Steam F1296NDS0", "стиральная машина"),
                Arguments.of("Bosch SPV2HKX5DR", "посудомоечная машина"));
    }
    @MethodSource("argsMethodSource")
    @ParameterizedTest(name = "проверка моделей {0} по соответсвию значениям: {1}")
    void searchTest3(String testData, String expectedResult) {

        searchImput.setValue(testData).pressEnter();
        Selenide.$$("li.result__item")
                .first()
                .shouldHave(Condition.text(expectedResult));
    }
}


