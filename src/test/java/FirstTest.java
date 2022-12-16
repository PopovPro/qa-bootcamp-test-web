import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.ErrorPage;

import static org.testng.AssertJUnit.assertEquals;

public class FirstTest extends Common {

    @Test
    public void getErrorPage() throws Exception {
        // Создаем экземпляр WebDriver
        WebDriver driver = new ChromeDriver();
        driver.get("http://checker.jettycloud.com/");
        // Находим элемент по названию класса

        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        String errorCode = driver.findElement(By.className("ant-result-title")).getText();
        assertEquals(errorCode, "404");
        String errorText = driver.findElement(By.className("ant-result-subtitle")).getText();
        assertEquals(errorText, "Стринца не найдена");
        // Закрываем браузер
        driver.close();
    }

    @Test
    public void getErrorPageWithPageObject() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("http://checker.jettycloud.com/");

        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");
        ErrorPage errorPage = new ErrorPage(driver);
        String errorCode = errorPage.getErrorCode();
        assertEquals(errorCode, "404");
        String errorText = errorPage.getErrorText();
        assertEquals(errorText, "Стринца не найдена");
        // Закрываем браузер
        driver.close();
    }
}
