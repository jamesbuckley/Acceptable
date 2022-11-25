import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;

@Epic("Allure examples")
@Feature("Junit 4 support")
public class UITest {

    @BeforeClass
    public void setup(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @Test()
    public void userCanSearch() {
        open("https://duckduckgo.com");
        $("#search_form_input_homepage").setValue("bbc");
        assert true;
    }

    @Test()
    public void userCanSearch2() {
        open("https://duckduckgo.com");
        $("#search_form_input_homepage").setValue("rte");
        assert true;
    }
}
