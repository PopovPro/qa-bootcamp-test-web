import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.ErrorPage;
import pages.MaterialsPage;
import pages.WarningPage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.AssertJUnit.*;

public class FirstTest extends Common {

    private final Logger logger = Logger.getLogger(FirstTest.class.getName());

    @Test
    public void checkErrorPage() throws Exception {
        // Создаем экземпляр WebDriver
        webDriver.get(baseUrl);
        // Находим элемент по названию класса

        String title = webDriver.getTitle();
        logger.info("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        String errorCode = webDriver.findElement(By.className("ant-result-title")).getText();
        assertEquals(errorCode, "404");
        String errorText = webDriver.findElement(By.className("ant-result-subtitle")).getText();
        assertEquals(errorText, "Стринца не найдена");
        // Закрываем браузер
    }

    @Test
    public void checkErrorPageWithPageObject() throws Exception {
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
    @Test
    public void checkMaterialsPage() throws Exception {
        webDriver.get(baseUrl.concat("/123"));

        String[] tabsNames = {"JAVA", "SQL", "Клиент-серверная архитектура"};
        List<String> expectedTabs = Arrays.asList(tabsNames);
        System.out.println("Tabs should be: " + expectedTabs);

        String title = webDriver.getTitle();
        System.out.println("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        MaterialsPage materialsPage = new MaterialsPage(webDriver);
        List<String> actualTabs = materialsPage.getTabsNames();
        System.out.println("Actual tab names are: " + actualTabs);

        AtomicInteger i = new AtomicInteger();
        actualTabs.forEach(tab -> {
            assertEquals(tab, expectedTabs.get(i.getAndIncrement()));
        });

        String checkBox = materialsPage.getCheckBoxText();
        assertEquals(checkBox, "Я ознакомился(лась) с правилами");
        System.out.println(checkBox);
        String button = materialsPage.getButtonText();
        System.out.println(button);
        assertEquals(button, "Перейти к тестированию");
    }

    @Test
    public void noAccessForUserWithNonExistingUid() throws Exception {
        // 1. Go to materials page with non-exisitnd UID
        webDriver.get(baseUrl.concat("/123"));
        MaterialsPage materialsPage = new MaterialsPage(webDriver);
        WarningPage warningPage = new WarningPage(webDriver);

        assertFalse(materialsPage.isButtonActive());

        // 2. Scroll the page and check checkbox
        WebElement checkBox = materialsPage.checkCkeckBox();
        //assertEquals(true, checkBox.isSelected());
        assertTrue(materialsPage.isButtonActive());

        // 3. Press the button "Перейти к тестированию"
        materialsPage.pressButton();
        Thread.sleep(1000);
        String resultText = warningPage.getResultText();
        System.out.println(resultText);
        assertEquals("Вы не можете пройти тест! Пожалуйста, напишите в поддержку, если вы считаете , что это ошибка.", resultText);
    }
}