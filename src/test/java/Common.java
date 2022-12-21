import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.Properties;

public class Common {

    public Common() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            RestAssured.baseURI = prop.getProperty("api.uri");
            RestAssured.port = Integer.parseInt(prop.getProperty("api.port"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    protected static Cookies getAuthCookies() {
        return RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "Rx8TMYPgz@ZUzb")
                .formParam("password", "64MX9j?csngTQv")
                .when()
                .post("/login")
                .getDetailedCookies();
    }
    WebDriver webDriver;
    String baseUrl;

    @BeforeTest
    public void setUp() {
        // Создаем экземпляр WebDriver
        webDriver = new ChromeDriver();
        baseUrl = "http://checker.jettycloud.com";
    }

    @AfterTest
    public void tearDown() {
        // Закрываем браузер
        webDriver.quit();
    }
}
