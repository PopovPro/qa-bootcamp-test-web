package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WarningPage {
    By result = By.className("ant-result-title");

    private final WebDriver driver;

    public WarningPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getResultText() {
        return driver.findElement(result).getText();
    }
}
