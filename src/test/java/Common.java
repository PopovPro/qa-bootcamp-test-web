import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public abstract class Common {

    WebDriver webDriver;
    String baseUrl;

    @BeforeTest
    public void setUp() {
        // Создаем экземпляр WebDriver
        webDriver = new ChromeDriver();
        baseUrl = "http://checker.jettycloud.com/";
    }

    @AfterTest
    public void tearDown() {
        // Закрываем браузер
        webDriver.quit();
    }
}
