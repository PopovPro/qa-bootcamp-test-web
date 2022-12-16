package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorPage {
    By errorCode = By.className("ant-result-title");
    By errorText = By.className("ant-result-subtitle");
    private final WebDriver driver;

    public ErrorPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getErrorCode() {
        return driver.findElement(errorCode).getText();
    }
    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }
}
