import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.ErrorPage;
import pages.MaterialsPage;
import pages.WarningPage;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class FirstTest extends Common {

    private final Logger logger = Logger.getLogger(FirstTest.class.getName());

    @Test(priority = 0, description = "Нет доступа к материалам при переходе по ссылке без UID")
    @Description("Описание: При переходе по доменному имени пользователь видит страницу ошибки")
    public void checkErrorPage() throws Exception {

        webDriver.get(baseUrl);
        // Находим элемент по названию класса

        String title = webDriver.getTitle();
        logger.info("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        String errorCode = webDriver.findElement(By.className("ant-result-title")).getText();
        assertEquals(errorCode, "404");
        String errorText = webDriver.findElement(By.className("ant-result-subtitle")).getText();
        assertEquals(errorText, "Стринца не найдена");
    }

    @Test(priority = 0, description = "Нет доступа к материалам при переходе по ссылке без UID. Используем Page Object")
    @Description("Описание: При переходе по доменному имени пользователь видит страницу ошибки")
    public void checkErrorPageWithPageObject() throws Exception {

        //используем общий метод из класса Common для создания драйвера
        webDriver.get(baseUrl);
        String title = webDriver.getTitle();
        logger.info("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        ErrorPage errorPage = new ErrorPage(webDriver);
        String errorCode = errorPage.getErrorCode();
        assertEquals(errorCode, "404");
        String errorText = errorPage.getErrorText();
        assertEquals(errorText, "Стринца не найдена");
    }

    @Test(priority = 0, description = "К тестирования нельзя перейти без согласия с правилами (нажать на чекбокс)")
    @Description("Описание: Проверка того, что только при согласии с правилами и нажатии чекбокс доступна кнопка 'Перейти к тесту'")
    public void checkMaterialsPage() throws Exception {
        webDriver.get(baseUrl.concat("/123"));

        String[] tabsNames = {"JAVA", "SQL", "Клиент-серверная архитектура"};
        List<String> expectedTabs = Arrays.asList(tabsNames);
        logger.info("Tabs should be: " + expectedTabs);

        String title = webDriver.getTitle();
        logger.info("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        MaterialsPage materialsPage = new MaterialsPage(webDriver);
        List<String> actualTabs = materialsPage.getTabsNames();
        logger.info("Actual tab names are: " + actualTabs);

        AtomicInteger i = new AtomicInteger();
        actualTabs.forEach(tab -> {
            assertEquals(tab, expectedTabs.get(i.getAndIncrement()));
        });

        String checkBox = materialsPage.getCheckBoxText();
        assertEquals(checkBox, "Я ознакомился(лась) с правилами");
        logger.info(checkBox);
        String button = materialsPage.getButtonText();
        logger.info(button);
        assertEquals(button, "Перейти к тестированию");
    }

    @Test(priority = 0, description = "Нет доступа к материалам при переходе к тесту с несуществующим в базе UID")
    @Description("Описание: Пользователь, даже с корретным айди, но не существующим в базе не может перейти к тестированию")
    public void noAccessForUserWithNonExistingUid() throws Exception {
        // 1. Go to materials page with non-exisitnd UID
        webDriver.get(baseUrl.concat("/123"));
        MaterialsPage materialsPage = new MaterialsPage(webDriver);
        WarningPage warningPage = new WarningPage(webDriver);

        assertFalse(materialsPage.isButtonActive());

        // 2. Scroll the page and check checkbox
        WebElement checkBox = materialsPage.checkCheckBox();
        //assertEquals(true, checkBox.isSelected());
        assertTrue(materialsPage.isButtonActive());

        // 3. Press the button "Перейти к тестированию"
        materialsPage.pressButton();
        Thread.sleep(1000);
        String resultText = warningPage.getResultText();
        logger.info(resultText);
        assertEquals("Вы не можете пройти тест! Пожалуйста, напишите в поддержку, если вы считаете , что это ошибка.", resultText);
    }
}
