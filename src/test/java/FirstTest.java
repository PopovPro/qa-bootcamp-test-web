import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.ErrorPage;

import static org.testng.AssertJUnit.assertEquals;

public class FirstTest extends Common {

    private final Logger logger = Logger.getLogger(FirstTest.class.getName());

    @Test
    public void getErrorPage() throws Exception {
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

    @Test
    public void getErrorPageWithPageObject() throws Exception {
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
}
