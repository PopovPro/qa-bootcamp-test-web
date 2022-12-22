import java.util.logging.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.ErrorPage;
import pages.MaterialsPage;
import pages.QuestionsPage;
import pages.WarningPage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.hasKey;
import static org.testng.AssertJUnit.*;

public class FirstTest extends Common {

    private final Logger logger = Logger.getLogger(FirstTest.class.getName());

    @Test
    public void checkErrorPage() throws Exception {
        // Создаем экземпляр WebDriver
        WebDriver driver = new ChromeDriver();
        driver.get("http://checker.jettycloud.com");
        // Находим элемент по названию класса
        String title = driver.getTitle();
        logger.info("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        String errorCode = driver.findElement(By.className("ant-result-title")).getText();
        assertEquals(errorCode, "404");
        String errorText = driver.findElement(By.className("ant-result-subtitle")).getText();
        assertEquals(errorText, "Стринца не найдена");
        // Закрываем браузер
        driver.close();
    }

    @Test
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
    @Test
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
        actualTabs.forEach(tab -> assertEquals(tab, expectedTabs.get(i.getAndIncrement())));

        String checkBox = materialsPage.getCheckBoxText();
        assertEquals(checkBox, "Я ознакомился(лась) с правилами");
        logger.info(checkBox);
        String button = materialsPage.getButtonText();
        logger.info(button);
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
        logger.info(resultText);
        assertEquals("Вы не можете пройти тест! Пожалуйста, напишите в поддержку, если вы считаете , что это ошибка.", resultText);
    }
    @Test
    public void checkQuestionsForUserWithExistingUid() throws Exception {
        // Create new user
        String userUid = UserApi.createUser();

        // Go to test page with user uid
        WebDriver driver = new ChromeDriver();
        driver.get(baseUrl.concat("/task/" + userUid));
        QuestionsPage questionsPage = new QuestionsPage(driver);
        //Thread.sleep(2000); //In case page load time is too big

        // Check first page
        assertEquals("Алексей Навальный", questionsPage.getUserName());
        assertTrue(questionsPage.getTimeLeft().contains("Time left"));
        assertTrue(questionsPage.timeChange());
        int stepsAmount = questionsPage.getAllStepsAmount();
        assertEquals(25, stepsAmount);
        logger.info("Total number of questions: " + stepsAmount);
        int activeQuestion = 1;
        assertEquals(activeQuestion, questionsPage.getActiveQuestion());
        logger.info("Active question: " + activeQuestion);
        assertEquals(24, questionsPage.getInactiveStepsAmount());
        assertEquals("Примером стека из жизни может быть:", questionsPage.getQuestionTitle());
        assertEquals(4, questionsPage.checkQuestionsAmountAndText());
        assertEquals("Следующий вопрос", questionsPage.getButtonText());
        assertFalse(questionsPage.isButtonActive());
        assertTrue(questionsPage.checkPageTitleAfterBtnClick());
        questionsPage.pickRandomAnswer();
        assertTrue(questionsPage.isButtonActive());
        questionsPage.clickNextQuestionBtn();

        //Check other pages
        while (activeQuestion < stepsAmount) {
            activeQuestion++;
            assertTrue(questionsPage.previousQuestionsChecked());
            assertEquals(activeQuestion, questionsPage.getActiveQuestion());
            logger.info("Active question: " + activeQuestion);
            //assertFalse(questionsPage.isButtonActive()); //bug its active on question 25
            switch(activeQuestion) {
                case 19:
                    questionsPage.sendInt();
                    break;
                case 22:
                case 23:
                    questionsPage.sendSql();
                    break;
                case 24:
                    questionsPage.sendJavaCode();
                    break;
                case 25:
                    assertEquals("Завершить тест", questionsPage.getButtonText());
                    questionsPage.sendJavaCode();
                    break;
                default:
                    questionsPage.pickRandomAnswer();
            }
            assertTrue(questionsPage.isButtonActive());
            questionsPage.clickNextQuestionBtn();
        }

        // Check test's final page
        assertEquals("http://checker.jettycloud.com/complete", questionsPage.checkURL());
        assertTrue(questionsPage.checkFinishedTestImg());
        assertEquals("Задание успешно отправлено! Мы дадим обратную связь до 13-го марта 2022г. Если обратная связь тебе не пришла, или у тебя возникли вопросы, пиши нам на почту.", questionsPage.finishedText());
        assertEquals("bootcamp@dins.ru", questionsPage.emailButtonText());

        //Close browser
        driver.close();

        //Check db
        /*
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://checker.jettycloud.com:9091"));
        DB database = mongoClient.getDB("{DB_DATABASE}");
        DBCollection results = database.getCollection("result");
        Object user = results.find({"uid": userUid});
         */
    }
}