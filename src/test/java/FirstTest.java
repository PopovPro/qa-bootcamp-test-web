import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;

public class FirstTest {
    @Test
    public void getMainPage() throws Exception {
        // Создаем экземпляр WebDriver
        WebDriver driver = new ChromeDriver();
        driver.get("http://checker.jettycloud.com/");
        // Находим элемент по названию класса
        WebElement element = driver.findElement(By.className("app__logo"));
        String logoText = element.getText().toLowerCase();
        System.out.println("Element is: " + element);

        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        assertEquals(title, "DINS QA Boot Camp check tool");

        // Закрываем браузер
        driver.quit();
    }
}