package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WarningPage {
    By result = By.className("ant-result-title");

    private final WebDriver driver;

    public WarningPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get successful results text on page: ")
    public String getResultText() {
        return driver.findElement(result).getText();
    }
}
