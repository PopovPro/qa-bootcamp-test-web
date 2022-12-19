import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.io.IOException;

public abstract class Common {

    WebDriver webDriver;
    String baseUrl;

    @BeforeTest
    public void setUp() {
        //Чтобы вычитать настройки из конфига
        ChromeOptions options = new ChromeOptions();
        Config config;
        {
            try {
                config = new Config();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Добаввляем опции для драйвера, чтобы запускать headless
        options.setHeadless(config.getIsHeadless());
        // Создаем экземпляр WebDriver
        webDriver = new ChromeDriver(options);
        baseUrl = config.getBaseUrl();
    }

    @AfterTest
    public void tearDown() {
        // Закрываем браузер
        webDriver.quit();
    }
}
