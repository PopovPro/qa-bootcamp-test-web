package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorPage {
    By errorCode = By.className("ant-result-title");
    By errorText = By.className("ant-result-subtitle");
    private final WebDriver driver;

    public ErrorPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get error code text")
    public String getErrorCode() {
        return driver.findElement(errorCode).getText();
    }
    @Step ("Get error text")
    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }
}
